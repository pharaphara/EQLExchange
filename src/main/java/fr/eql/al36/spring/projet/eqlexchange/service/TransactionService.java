package fr.eql.al36.spring.projet.eqlexchange.service;

import com.google.common.hash.Hashing;
import fr.eql.al36.spring.projet.eqlexchange.domain.*;
import fr.eql.al36.spring.projet.eqlexchange.repository.AssetRepository;
import fr.eql.al36.spring.projet.eqlexchange.repository.CurrencyPriceRepository;
import fr.eql.al36.spring.projet.eqlexchange.repository.TradeOrderRepository;
import fr.eql.al36.spring.projet.eqlexchange.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TransactionService {

    private final double MAX_SLIPPAGE_RATE = .97;

    private final AssetService assetService;

    private final TradeOrderService tradeOrderService;

    private final TransactionRepository transactionRepository;

    private final TradeOrderRepository tradeOrderRepository;

    private final AssetRepository assetRepository;

    private final CurrencyPriceRepository currencyPriceRepository;

    private final CurrencyPriceService currencyPriceService;

    private final UserService userService;


    public TransactionService(AssetService assetService, TradeOrderService tradeOrderService,
                              TransactionRepository transactionRepository, TradeOrderRepository tradeOrderRepository,
                              AssetRepository assetRepository, CurrencyPriceRepository currencyPriceRepository,
                              CurrencyPriceService currencyPriceService, UserService userService) {

        this.assetService = assetService;
        this.tradeOrderService = tradeOrderService;
        this.transactionRepository = transactionRepository;
        this.tradeOrderRepository = tradeOrderRepository;
        this.assetRepository = assetRepository;
        this.currencyPriceRepository = currencyPriceRepository;
        this.currencyPriceService = currencyPriceService;
        this.userService = userService;
    }


    public List<Transaction> getTransactionsDoneByUser(User user) {
        List<Asset> assets = assetRepository.getAllByUser(user);
        return transactionRepository.findAllByAssets(assets);
    }


    public List<Transaction> getTransactionsByAsset(Asset asset) {
        return transactionRepository.findAllByAsset(asset);
    }


    public List<Transaction> getTransactionsDoneOnCurrency(Currency currency) {
        List<Asset> assets = assetRepository.getAllByCurrency(currency);
        return transactionRepository.findAllByAssets(assets);
    }


    public void executeFromTradeOrders(TradeOrder tradeOrder1, TradeOrder tradeOrder2) {


        if(tradeOrder1.getCurrencyToBuy().getId() != tradeOrder2.getCurrencyToSell().getId()
           || tradeOrder1.getCurrencyToSell().getId() != tradeOrder2.getCurrencyToBuy().getId()) {
            return;
        }

        boolean isEven = false;
        if(tradeOrder1.getAmountToBuy() / tradeOrder2.getAmountToSell() >= MAX_SLIPPAGE_RATE
           && tradeOrder2.getAmountToBuy() / tradeOrder1.getAmountToSell() >= MAX_SLIPPAGE_RATE) {
            isEven = true;
        }


        // FIRST TRANSACTION
        // BUY TRADE ORDER 1
        // SELL TRADE ORDER 2
        // AMOUNT : tradeOrder1.getAmountToBuy / tradeOrder2.getAmountToSell
        // CURRENCY : tradeOrder1.getCurrencyToBuy / tradeOrder2.getCurrencyToSell

        double amount;
        if(isEven) {
            amount = tradeOrder2.getAmountToSell();
        } else {
            amount = (Math.min(tradeOrder1.getAmountToBuy(), tradeOrder2.getAmountToSell()));

        }

        User tradeOrder1User = tradeOrder1.getUser();
        User tradeOrder2User = tradeOrder2.getUser();

        Currency currency = tradeOrder2.getCurrencyToSell();
        Asset targetAsset = assetService.getByUserAndCurrency(tradeOrder1User, currency);
        Asset sourceAsset = assetService.getByUserAndCurrency(tradeOrder2User, currency);
        assetService.creditFromSourceAsset(targetAsset, sourceAsset, amount);
        Transaction transaction = Transaction.builder()
                .sourceAsset(sourceAsset)
                .targetAsset(targetAsset)
                .amount(amount)
                .date(LocalDateTime.now())
                .build();
        transaction.setTxId(hash256(transaction.toString()));
        transactionRepository.save(transaction);

        // SECOND TRANSACTION
        // BUY TRADE ORDER 2
        // SELL TRADE ORDER 1
        // AMOUNT : tradeOrder2.getAmountToBuy / tradeOrder1.getAmountToSell
        // CURRENCY : tradeOrder2.getCurrencyToBuy / tradeOrder1.getCurrencyToSell

        if(isEven) {
            amount = tradeOrder1.getAmountToSell();
        } else {
            amount = (Math.min(tradeOrder2.getAmountToBuy(), tradeOrder1.getAmountToSell()));
        }
        currency = tradeOrder1.getCurrencyToSell();
        targetAsset = assetService.getByUserAndCurrency(tradeOrder2User, currency);
        sourceAsset = assetService.getByUserAndCurrency(tradeOrder1User, currency);
        assetService.creditFromSourceAsset(targetAsset, sourceAsset, amount);
        transaction = Transaction.builder()
                .sourceAsset(sourceAsset)
                .targetAsset(targetAsset)
                .amount(amount)
                .date(LocalDateTime.now())
                .build();
        transaction.setTxId(hash256(transaction.toString()));
        transactionRepository.save(transaction);

        if(!isEven) {
            TradeOrder biggerTradeOrder;
            TradeOrder smallerTradeOrder;

            if(tradeOrder1.getAmountToBuy() > tradeOrder2.getAmountToSell()) {
                biggerTradeOrder = tradeOrder1;
                smallerTradeOrder = tradeOrder2;

            } else {
                biggerTradeOrder = tradeOrder2;
                smallerTradeOrder = tradeOrder1;
            }

            smallerTradeOrder.setCompletionDate(LocalDateTime.now());
            biggerTradeOrder.setCancellationDate(LocalDateTime.now());

            tradeOrderRepository.save(smallerTradeOrder);
            tradeOrderRepository.save(biggerTradeOrder);

            tradeOrderService.place(TradeOrder.builder()
                                            .user(biggerTradeOrder.getUser())
                                            .currencyToBuy(biggerTradeOrder.getCurrencyToBuy())
                                            .currencyToSell(biggerTradeOrder.getCurrencyToSell())
                                            .amountToBuy(biggerTradeOrder.getAmountToBuy() -
                                                         smallerTradeOrder.getAmountToSell())
                                            .amountToSell(biggerTradeOrder.getAmountToSell() -
                                                          smallerTradeOrder.getAmountToBuy())
                                            .creationDate(LocalDateTime.now())
                                            .build());
        } else {
            tradeOrder1.setCompletionDate(LocalDateTime.now());
            tradeOrder2.setCompletionDate(LocalDateTime.now());
            tradeOrderRepository.save(tradeOrder1);
            tradeOrderRepository.save(tradeOrder2);
        }
    }


    public void executeFromPayment(Payment payment) {

        User owner = userService.findUserById(1);
        Asset sourceAsset = assetService.getByUserAndCurrency(owner, payment.getCurrency());
        Asset targetAsset = payment.getAsset();
        double amount = payment.getAmount();

        assetService.creditFromSourceAsset(targetAsset, sourceAsset, amount);

        Transaction transaction = Transaction.builder()
                .targetAsset(targetAsset)
                .amount(amount)
                .date(LocalDateTime.now())
                .build();
        transaction.setTxId(hash256(transaction.toString()));
        transactionRepository.save(transaction);
    }


    public Transaction transferMoney(User targetUser, Asset sourceAsset, Transaction transaction) {
        Currency currency = sourceAsset.getCurrency();
        Asset targetAsset = assetService.findAssetByUserAndCurrency(targetUser, currency);
        if(targetAsset == null) {
            targetAsset = Asset.builder().user(targetUser).currency(currency).balance(0).build();
        }

        sourceAsset.setBalance(sourceAsset.getBalance() - transaction.getAmount());
        assetRepository.save(sourceAsset);

        targetAsset.setBalance(targetAsset.getBalance() + transaction.getAmount());
        assetRepository.save(targetAsset);

        transaction = Transaction.builder()
                .targetAsset(targetAsset)
                .sourceAsset(sourceAsset)
                .amount(transaction.getAmount())
                .date(LocalDateTime.now())
                .build();
        transaction.setTxId(hash256(transaction.toString()));
        transactionRepository.save(transaction);
        return transaction;
    }

    private String hash256(String originalString) {
        String hashedString = Hashing.sha256().hashString(originalString, StandardCharsets.UTF_8).toString();
        return Hashing.sha256().hashString(hashedString, StandardCharsets.UTF_8).toString();
    }
}