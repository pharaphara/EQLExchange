package fr.eql.al36.spring.projet.eqlexchange.service;

import fr.eql.al36.spring.projet.eqlexchange.domain.Asset;
import fr.eql.al36.spring.projet.eqlexchange.domain.Currency;
import fr.eql.al36.spring.projet.eqlexchange.domain.User;
import fr.eql.al36.spring.projet.eqlexchange.repository.AssetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetService {

    private AssetRepository assetRepository;
    private CurrencyService currencyService;

    public void creditFromSourceAsset(Asset targetAsset, Asset sourceAsset, double amount) {
        if (targetAsset.getCurrency() == sourceAsset.getCurrency() && sourceAsset.getBalance() >= amount) {
            sourceAsset.setBalance(sourceAsset.getBalance() - amount);
            targetAsset.setBalance(targetAsset.getBalance() + amount);
        }
    }

    public List<Asset> getUserWallet(User user) {
        return assetRepository.getAssetsByUserOrderByBalanceDesc(user);
    }

    public Asset getByUserAndCurrency(User user, Currency currency) {
        return assetRepository.getAssetByUserAndCurrency(user, currency);
    }

    public List<Asset> getAllByUser(User user) {
        return assetRepository.getAllByUser(user);
    }

    public List<Asset> getAllByCurrency(Currency currency) {
        return assetRepository.getAllByCurrency(currency);
    }

    public Asset getById(Integer id) {
        return assetRepository.findById(id).get();
    }
}
