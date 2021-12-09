package fr.eql.al36.spring.projet.eqlexchange.controller;

import fr.eql.al36.spring.projet.eqlexchange.domain.Asset;
import fr.eql.al36.spring.projet.eqlexchange.domain.Payment;
import fr.eql.al36.spring.projet.eqlexchange.domain.User;
import fr.eql.al36.spring.projet.eqlexchange.service.AssetService;
import fr.eql.al36.spring.projet.eqlexchange.service.CurrencyService;
import fr.eql.al36.spring.projet.eqlexchange.service.PaymentService;
import fr.eql.al36.spring.projet.eqlexchange.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class TransactionController {

    private final TransactionService transactionService;
    private final CurrencyService currencyService;
    private final PaymentService paymentService;
    private final AssetService assetService;

    public TransactionController(TransactionService transactionService, CurrencyService currencyService, PaymentService paymentService, AssetService assetService) {
        this.transactionService = transactionService;
        this.currencyService = currencyService;
        this.paymentService = paymentService;
        this.assetService = assetService;
    }

    @GetMapping("wallet/history")
    public String displayWallet(Model model, HttpSession session) {
        User connectedUser = (User) session.getAttribute("sessionUser");
        model.addAttribute("sessionUser", connectedUser);
        //model.addAttribute("transactions", transactionService.getTransactionsDoneByUser(connectedUser));
        return "wallet/history";
    }

    @GetMapping("wallet/refill")
    public String refillWallet(Model model, HttpSession session) {
        User connectedUser = (User) session.getAttribute("sessionUser");
        model.addAttribute("sessionUser", connectedUser);
        model.addAttribute("fiatCurrencies",currencyService.getFiatCurrencies());
        model.addAttribute("payment", new Payment());
        return "transaction/fillup";
    }

    @PostMapping("/wallet/refill/pay")
    public String payRefill(Model model, @ModelAttribute Payment payment, HttpSession session) {
        User connectedUser = (User) session.getAttribute("sessionUser");
        Asset targetAsset = assetService.getByUserAndCurrency(connectedUser,payment.getCurrency());
        payment.setAsset(targetAsset);
        paymentService.execute(payment);

        return "redirect:/wallet/";
    }

}
