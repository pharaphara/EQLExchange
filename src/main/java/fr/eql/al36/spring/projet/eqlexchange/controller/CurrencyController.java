package fr.eql.al36.spring.projet.eqlexchange.controller;

import fr.eql.al36.spring.projet.eqlexchange.domain.Currency;
import fr.eql.al36.spring.projet.eqlexchange.service.CurrencyPriceService;
import fr.eql.al36.spring.projet.eqlexchange.service.CurrencyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyController {

    private final CurrencyService currencyService;

    private final CurrencyPriceService currencyPriceService;


    public CurrencyController(CurrencyService currencyService, CurrencyPriceService currencyPriceService) {
        this.currencyService = currencyService;
        this.currencyPriceService = currencyPriceService;
    }

    @GetMapping("currency/{id}/details")
    public ResponseEntity<Currency> displayCurrencyDetails(@PathVariable String id) {
        Currency currency = currencyService.findCurrencyById(Integer.parseInt(id));

        if(currency == null){
            return null;
        }

        return new ResponseEntity<>(currency, HttpStatus.OK);
    }

    @GetMapping("currency/{id}/prices")
    public ResponseEntity<String> getCurrencyPrices(@PathVariable("id") String id) {
        Currency currency = currencyService.findCurrencyById(Integer.parseInt(id));
        String currencyPriceJson = currencyPriceService.getCurrencyPricesJSON(currency);
        return new ResponseEntity<>(currencyPriceJson, HttpStatus.OK);
    }

}
