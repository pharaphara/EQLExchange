package fr.eql.al36.spring.projet.eqlexchange.controller;

import fr.eql.al36.spring.projet.eqlexchange.domain.Asset;
import fr.eql.al36.spring.projet.eqlexchange.domain.Currency;
import fr.eql.al36.spring.projet.eqlexchange.domain.User;
import fr.eql.al36.spring.projet.eqlexchange.service.AssetService;
import fr.eql.al36.spring.projet.eqlexchange.service.CurrencyPriceService;
import fr.eql.al36.spring.projet.eqlexchange.service.TransactionService;
import fr.eql.al36.spring.projet.eqlexchange.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class WalletController {

    private final AssetService assetService;
    private final CurrencyPriceService currencyPriceService;
    private final TransactionService transactionService;
    private final UserService userService;

    public WalletController(AssetService assetService, CurrencyPriceService currencyPriceService, TransactionService transactionService, UserService userService) {
        this.assetService = assetService;
        this.currencyPriceService = currencyPriceService;
        this.transactionService = transactionService;
        this.userService = userService;
    }

    private User getConnectedUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        User connectedUser = userService.findUserByEmail(username);
        return connectedUser;
    }

    @GetMapping("wallet")
    public ResponseEntity<List<Asset>> getUserAssets() {
        List<Asset> assets = assetService.getUserWallet(getConnectedUser());
        return new ResponseEntity<>(assets, HttpStatus.OK);
    }

    @GetMapping("wallet/{id}")
    public ResponseEntity<Asset> getAssetDetails(@PathVariable String id) {
        Asset asset = assetService.getById(Integer.parseInt(id));
        return new ResponseEntity<>(asset, HttpStatus.OK);
    }
}
