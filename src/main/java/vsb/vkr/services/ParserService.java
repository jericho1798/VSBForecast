package vsb.vkr.services;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import vsb.vkr.dto.Fresh;
import vsb.vkr.dto.currencyNames;
import vsb.vkr.parser.ParserImplTorForex;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class ParserService {

    public static List<Fresh> parse(String url) throws IOException {
        List<Fresh> forecastList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        ParserImplTorForex parserImplTorForex = new ParserImplTorForex();
        Document document = Jsoup.connect(url).get();
        Set<String> URLS = new HashSet<>();
//        List<Forecast> finalForecasts = Arrays.asList(mapper.readValue(Paths.get("forecasts.json").toFile(), Forecast[].class));
        List<Fresh> badForecasts = new ArrayList<>();
        for (vsb.vkr.dto.currencyNames currencyNames : currencyNames.values()) {
            URLS.add(document.select("ul.sub-menu").select("a:contains(Прогноз " + currencyNames + ")").attr("href"));
        }
        for (String URL : URLS) {
            List<Fresh> allForecasts = parserImplTorForex.parseArticlesLink(URL, false);
            allForecasts.forEach(l -> {
                if (l.getDirection() == '-' || l.getDate().equals("") || l.getCurrencyPairName().equals("") || l.getCurrencyPairName().equals(" ") || l.getMin() == 0 || l.getMax() == 0) {
                    badForecasts.add(l);
                } else {
                    forecastList.add(l);
                }
            });
        }
        return forecastList;
    }
}
