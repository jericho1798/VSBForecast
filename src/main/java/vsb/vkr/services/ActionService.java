package vsb.vkr.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import vsb.vkr.dto.Forecast;
import vsb.vkr.dto.Fresh;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ActionService {
    public List<Forecast> getForecastsJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Forecast> finalForecasts = Arrays.asList(mapper.readValue(Paths.get("forecast.json").toFile(), Forecast[].class));
        finalForecasts.forEach(ff -> {
            try {
                ff.setRealValue(getRealValue(ff.getCurrencyPairName().replaceAll("/", ""), ff.getDate()));
                ff.setRate(rateCalc(ff.getDirection(), ff.getMin(), ff.getMax(), ff.getRealValue()));
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        log.info("Получение списка прогнозов");
        return finalForecasts.stream().filter(p -> p.getRate() != -1).collect(Collectors.toList());
    }

    public float getRealValue(String name, String date) throws IOException {
        String url = "C:\\Users\\vladi\\Desktop\\vkr\\src\\main\\resources\\" + name + ".htm";
        File file = new File(url);
        Document doc;
        String aa = "";
        doc = Jsoup.parse(file, "UTF-8");
        Elements title = doc.select("tr");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.ENGLISH);
        LocalDate date1 = LocalDate.parse(date, formatter);
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy.MM.dd", Locale.ENGLISH);

//        for (Element t : title) {
//            if (!t.select("td").get(0).text().equals("Date")) {
//                LocalDate date2 = LocalDate.parse(t.select("td").get(0).text(), formatter1);
//                if (date1.equals(date2.minus(Period.ofDays(1))))
//                    aa = t.select("td").get(4).text();
//            }
//        }
        for (int i = 1; i < title.size() - 1; i++) {
            LocalDate date2 = LocalDate.parse(title.get(i + 1).select("td").get(0).text(), formatter1);
            if (date1.plus(Period.ofDays(1)).equals(date2)) {
                aa = title.get(i + 1).select("td").get(4).text();
            } else {
                if (date1.plus(Period.ofDays(3)).equals(date2)) {
                    aa = title.get(i + 1).select("td").get(4).text();
                }
            }
        }
        if (aa.equals("")) {
            log.info("BAD: {}, {}", name, date);
            aa = "-1";
        }
        return Float.parseFloat(aa);
    }

    public float rateCalc(char direction, float min, float max, float real) {
        if (real != -1F) {
            switch (direction) {
                case 'u':
                    if (real > min && real < max) {
                        return 0.5F;
                    } else if (real >= max) {
                        return 1.0F;
                    } else {
                        return 0;
                    }
                case 'd':
                    if (real > min && real < max) {
                        return 0.5F;
                    } else if (real <= min) {
                        return 1.0F;
                    } else {
                        return 0;
                    }
                default:
                    return 0;
            }
        } else {
            return -1;
        }
    }

    public List<Fresh> getFreshForecast() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
//        List<Fresh> finalForecasts = Arrays.asList(mapper.readValue(Paths.get("fresh.json").toFile(), Fresh[].class))
        List<Fresh> finalForecasts = ParserService.parse("https://torforex.com");
//        log.info("Получение списка прогнозов");
        return finalForecasts;
    }
}