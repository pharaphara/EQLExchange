package fr.eql.al36.spring.projet.eqlexchange.repository;

import fr.eql.al36.spring.projet.eqlexchange.domain.Asset;
import fr.eql.al36.spring.projet.eqlexchange.domain.Currency;
import fr.eql.al36.spring.projet.eqlexchange.domain.TradeOrder;
import fr.eql.al36.spring.projet.eqlexchange.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface TradeOrderRepository extends CrudRepository<TradeOrder, Integer> {

    List<TradeOrder> getAllByAssetIn(List<Asset> assets);

    @Query("SELECT to FROM TradeOrder to WHERE to.currencyToBuy = :currencyToSell AND to.currencyToSell = :currencyToBuy AND to.completionDate IS NULL AND to.cancellationDate IS NULL AND to.user <> :user ORDER by to.amountToBuy")
    List<TradeOrder> findAllMatchingTradeOrders(@Param("user") User user, @Param("currency_to_sell") Currency currencyToSell, @Param("wanted_currency") Currency currencyToBuy);

}
