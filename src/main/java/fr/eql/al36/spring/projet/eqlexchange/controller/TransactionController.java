package fr.eql.al36.spring.projet.eqlexchange.controller;

import fr.eql.al36.spring.projet.eqlexchange.domain.*;
import fr.eql.al36.spring.projet.eqlexchange.security.JpaUserDetailsService;
import fr.eql.al36.spring.projet.eqlexchange.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class TransactionController {

    private final TransactionService transactionService;

    private final CurrencyService currencyService;

    private final PaymentService paymentService;

    private final AssetService assetService;

    private final UserService userService;

    private final JpaUserDetailsService userDetailsService;

    private final CurrencyPriceService currencyPriceService;


    public TransactionController(TransactionService transactionService, CurrencyService currencyService,
                                 PaymentService paymentService, AssetService assetService,
                                 UserService userService, JpaUserDetailsService userDetailsService, CurrencyPriceService currencyPriceService) {
        this.transactionService = transactionService;
        this.currencyService = currencyService;
        this.paymentService = paymentService;
        this.assetService = assetService;
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.currencyPriceService = currencyPriceService;
    }

    private User getConnectedUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        User connectedUser = userService.findUserByEmail(username);
        return connectedUser;
    }

    @GetMapping("wallet/history")
    public ResponseEntity<List<Transaction>> getUserTransactions() {
        List<Transaction> transactionList = transactionService.getTransactionsDoneByUser(getConnectedUser());
        return new ResponseEntity<>(transactionList, HttpStatus.OK);
    }


    @GetMapping("wallet/refill")
    public String refillWallet(Model model, HttpSession session) {
        User connectedUser = (User) session.getAttribute("sessionUser");
        model.addAttribute("sessionUser", connectedUser);
        model.addAttribute("fiatCurrencies", currencyService.getFiatCurrencies());
        model.addAttribute("payment", new Payment());
        return "transaction/fillup";
    }


    @PostMapping("/wallet/refill/pay")
    public String payRefill(Model model, @ModelAttribute Payment payment, HttpSession session) {
        User connectedUser = (User) session.getAttribute("sessionUser");
        Asset targetAsset = assetService.getByUserAndCurrency(connectedUser, payment.getCurrency());
        payment.setAsset(targetAsset);
        paymentService.execute(payment);

        return "redirect:/wallet/";
    }


    @GetMapping("wallet/{id}/transfer")
    public String makeTransfer(Model model, HttpSession session, @PathVariable String id) {
        User connectedUser = (User) session.getAttribute("sessionUser");
        Currency currency = assetService.getById(Integer.parseInt(id)).getCurrency();
        model.addAttribute("currencyPricesJSON", currencyPriceService.getCurrencyPricesJSON(currency));
        model.addAttribute("sessionUser", connectedUser);
        model.addAttribute("sourceAsset", assetService.getById(Integer.parseInt(id)));
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("targetUser", new User());
        return "transaction/transfer";
    }


    @PostMapping("wallet/save-transaction")
    public String saveTransaction(Model model, @ModelAttribute User targetUser, @ModelAttribute Transaction transaction,
                                  @ModelAttribute Asset sourceAsset, HttpSession session) {
        User connectedUser = (User) session.getAttribute("sessionUser");
        model.addAttribute("sessionUser", connectedUser);
        String walletAddress = targetUser.getWalletAddress();

        targetUser = userService.findUserWalletAddress(walletAddress);
        if(targetUser == null) {
            model.addAttribute("walletAddress", walletAddress);
            return "transaction/address-non-existant";
        }

        sourceAsset = assetService.getById(sourceAsset.getId());
        if(sourceAsset.getBalance() < transaction.getAmount()) {
            model.addAttribute("asset", sourceAsset);
            return "transaction/not-enough-funds";
        }

        transactionService.transferMoney(targetUser, sourceAsset, transaction);
        return "redirect:/wallet/" + sourceAsset.getId();
    }

}
