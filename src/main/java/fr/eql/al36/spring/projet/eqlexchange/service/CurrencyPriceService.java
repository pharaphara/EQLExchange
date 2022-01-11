package fr.eql.al36.spring.projet.eqlexchange.service;

import com.google.gson.Gson;
import fr.eql.al36.spring.projet.eqlexchange.domain.Currency;
import fr.eql.al36.spring.projet.eqlexchange.domain.CurrencyPrice;
import fr.eql.al36.spring.projet.eqlexchange.dto.CurrencyPriceDTO;
import fr.eql.al36.spring.projet.eqlexchange.repository.CurrencyPriceRepository;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CurrencyPriceService {

    private final CurrencyPriceRepository currencyPriceRepository;


    public CurrencyPriceService(
            CurrencyPriceRepository currencyPriceRepository) {
        this.currencyPriceRepository = currencyPriceRepository;
    }


    public CurrencyPrice getLatestPriceOFCurrency(Currency currency) {
        return currencyPriceRepository.findTopByCurrencyOrderByIdDesc(currency);

    }


    public List<CurrencyPrice> getPricesOfCurrency(Currency currency) {
        return currencyPriceRepository.getAllByCurrencyOrderByDateTimeAsc(currency);
    }


    public String getCurrencyPricesJSON(Currency currency) {
        List<CurrencyPriceDTO> currencyPriceDTOS = new ArrayList<>();
        for(CurrencyPrice currencyPrice : getPricesOfCurrency(currency)) {
            currencyPriceDTOS.add(CurrencyPriceDTO.toDto(currencyPrice));
        }
        return new Gson().toJson(currencyPriceDTOS);
    }

    public List<CurrencyPrice> generateRandomCurrencyPrices(Currency currency, LocalDateTime startDateTime, Integer interval, double startPrice) {
        List<CurrencyPrice> currencyPrices = new ArrayList<>();

        LocalDateTime dateTime = startDateTime;
        double price = startPrice;

        while (dateTime.isBefore(LocalDateTime.now())) {
            CurrencyPrice currencyPrice = new CurrencyPrice();
            currencyPrice.setCurrency(currency);
            double randomNumber = Math.random();
            double randomPricePart = price * Math.random();
            if (Math.random() > .5) price += randomPricePart * randomNumber;
            else price -= randomPricePart * randomNumber;
            currencyPrice.setPrice(price);
            currencyPrice.setDateTime(dateTime);
            currencyPrices.add(currencyPrice);
            dateTime = dateTime.plusSeconds(interval);
        }
        return currencyPrices;
    }

    public List<CurrencyPrice> generateLinearCurrencyPrices(Currency currency, LocalDateTime startDateTime, Integer interval, double value) {
        List<CurrencyPrice> currencyPrices = new ArrayList<>();

        LocalDateTime dateTime = startDateTime;

        while (dateTime.isBefore((LocalDateTime.now()))) {
            double fluctuatingValue;
            double randomValue = value * Math.random() * .3;
            if (Math.random() > .5) fluctuatingValue = value + randomValue;
            else fluctuatingValue = value - randomValue;
            CurrencyPrice currencyPrice = new CurrencyPrice();
            currencyPrice.setCurrency(currency);
            currencyPrice.setPrice(fluctuatingValue);
            currencyPrice.setDateTime(dateTime);
            currencyPrices.add(currencyPrice);
            dateTime = dateTime.plusSeconds(interval);
        }
        return currencyPrices;
    }

    @Transactional
    public void saveCurrencyPrices(List<CurrencyPrice> currencyPrices) {
        for (CurrencyPrice currencyPrice : currencyPrices) {
            currencyPrice = currencyPriceRepository.save(currencyPrice);
        }
    }
}
