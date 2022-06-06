package vsb.vkr.repos;

import org.springframework.data.repository.CrudRepository;
import vsb.vkr.dto.Forecast;

public interface ForecastRepo extends CrudRepository<Forecast, Long> {
    Iterable<Forecast> findByName(String name);
    Iterable<Forecast> findByCurrencyPairName(String currencyPairName);
    Iterable<Forecast> findAllByOrderByIdDesc();
}
