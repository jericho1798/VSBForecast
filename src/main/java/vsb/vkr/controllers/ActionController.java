package vsb.vkr.controllers;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vsb.vkr.dto.Exp;
import vsb.vkr.dto.Forecast;
import vsb.vkr.dto.Fresh;
import vsb.vkr.repos.ForecastRepo;
import vsb.vkr.repos.FreshRepo;
import vsb.vkr.services.ActionService;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("api/forecasts")
public class ActionController {
    //    @Autowired
//    private ForecastRepo forecastRepo;
//    @Autowired
//    private FreshRepo freshRepo;
    @Autowired
    private ActionService actionService;
    List<Forecast> forecasts = new ArrayList<>();

    public ActionController(ActionService actionService) {
        //  this.forecastRepo = forecastRepo;
        this.actionService = actionService;
    }

//    @GetMapping(value = "getForecasts", produces = MediaType.APPLICATION_JSON_VALUE)
//    public void getForecast() throws IOException {
////        forecastRepo.deleteAll();
//        log.info("Заполнение");
//        List<Forecast> forecasts = actionService.getForecastsJson();
//        Collections.reverse(forecasts);
//        forecastRepo.saveAll(forecasts);
//    }
//    @GetMapping(value = "saveFreshForecasts", produces = MediaType.APPLICATION_JSON_VALUE)
//    public void saveFreshForecast() throws IOException {
//        log.info("Поиск свежих прогнозов");
//        List<Fresh> forecasts = actionService.getFreshForecast();
//        Collections.reverse(forecasts);
//        freshRepo.saveAll(forecasts);
//    }

    @GetMapping(value = "getFreshForecasts", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Fresh> getFreshForecast() throws IOException {
        log.info("Получение свежих прогнозов");
        return actionService.getFreshForecast();
//        return (List<Fresh>) freshRepo.findAll();
    }


    @GetMapping
    public List<Forecast> allForecasts(@RequestParam(required = false) String currencyPairName) throws IOException {
//        if (ObjectUtils.isEmpty(currencyPairName)) {
//            log.info("Getting all forecasts");
//            return (List<Forecast>) forecastRepo.findAllByOrderByIdDesc();
//        }
//        log.info(currencyPairName);
//
//        return (List<Forecast>) forecastRepo.findByCurrencyPairName(currencyPairName.toUpperCase());
//        forecasts = actionService.getForecastsJson();
//        Collections.reverse(forecasts);
        ObjectMapper mapper = new ObjectMapper();
        List<Forecast> finalForecasts = Arrays.asList(mapper.readValue(Paths.get("mockedForecasts.json").toFile(), Forecast[].class));
        Collections.reverse(finalForecasts);
        log.info("DONE");
        return finalForecasts;
    }


    @GetMapping("/rating")
    public Exp rating(Map<String, Object> model) {
        String i = "TORFOREX.COM";
        Exp exp = new Exp(i, numberOfForecasts(i), calculateNumbers(i, 0), calculateNumbers(i, 0.5), calculateNumbers(i, 1));
        log.info("RATING");
        return exp;
    }

    public int numberOfForecasts(String name) {
//        Iterable<Forecast> forecasts = forecastRepo.findByName(name);
//        return (int) forecasts.spliterator().getExactSizeIfKnown();
        return forecasts.size();
    }

    public int calculateNumbers(String name, double value) {
        int out = 0;
        for (Forecast forecast : forecasts) {
            if (forecast.getRate() == value) {
                out++;
            }
        }
        return out;
    }

}

