package vsb.vkr.parser;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import vsb.vkr.dto.Fresh;
import vsb.vkr.nlp.NlpImplTorForex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ParserImplTorForex implements Parser {
    @Override
    public List<Fresh> parseArticlesLink(String baseUrl, boolean allPageScan) {
        List<Fresh> fullList = new ArrayList<>();
        List<String> visited = Arrays.asList("https://torforex.com/prognoz-forex/eurusd-prognoz-evro-dollar-na-6-10-iyunya-2022/",
                "https://torforex.com/prognoz-forex/usdchf-prognoz-kursa-franka-na-6-10-iyunya-2022/",
                "https://torforex.com/prognoz-forex/gbpusd-prognoz-forex-na-6-10-iyunya-2022/",
                "https://torforex.com/prognoz-forex/usdcad-prognoz-kanadskij-dollar-na-6-10-iyunya-2022/",
                "https://torforex.com/prognoz-forex/tehnicheskij-analiz-nzdusd-na-6-10-iyunya-2022/",
                "https://torforex.com/prognoz-forex/forex-prognoz-audusd-na-6-10-iyunya-2022/",
                "https://torforex.com/prognoz-forex/usdjpy-prognoz-dollar-iena-na-6-10-iyunya-2022/"
        );
        try {
            int lastPage = 1;
            Document document;
            if (allPageScan) {
                document = Jsoup.connect(baseUrl).get();
                Elements elements = document.select("div.nav-links");
                String[] pages = elements.text().split(" ");
                lastPage = Integer.parseInt(pages[pages.length - 2]);
                System.out.println(lastPage);
            }
            for (int i = 1; i <= lastPage; i++) {
                String url = baseUrl + "page/" + i + "/";
                System.out.println("URL: " + url);
                document = Jsoup.connect(url).get();
                Elements title = document.select("article");
                Elements links = title.select("a.more-link");
                for (Element l : links) {
                    if (visited.contains(l.attr("href"))) {
                        System.out.println("Reached");
                        break;
                    } else {
                        Fresh forecast = parseData(l.attr("href"));
                        if (!forecast.getName().equals("trash") && !forecast.getDate().equals("trash"))
                            fullList.add(forecast);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fullList;
    }

    @Override
    public Fresh parseData(String url) {
        Document document = null;
        Fresh forecast = new Fresh();
        NlpImplTorForex nlpImplTorForex = new NlpImplTorForex();
        forecast.setName("TORFOREX.COM");
        try {
            forecast.setLink(url);
            document = Jsoup.connect(forecast.getLink()).get();
            String article = document.select("p:contains(таким образом)").text();
            String title = document.title();
            //SET PAIR NAME
            forecast.setCurrencyPairName(nlpImplTorForex.getPairName(title.toUpperCase()));
            if (forecast.getCurrencyPairName().equals("") || forecast.getCurrencyPairName() == null) {
                forecast.setCurrencyPairName(nlpImplTorForex.getPairName(article).toUpperCase());
            }
            //SET FORECAST DATE
            forecast.setDate(nlpImplTorForex.getForecastDate(title));
            if (forecast.getDate().equals("") || forecast.getDate() == null) {
                forecast.setDate(nlpImplTorForex.getForecastDate(article));
            }
            //TOKENIZE
            String[] tokens = nlpImplTorForex.tokenize(article);
            //SET MIN AND MAX VALUES
            List<Float> list = nlpImplTorForex.minMaxValues(tokens);
            if (list.size() == 0) {
                String fullArticle = document.select("article").text();
                List<Float> allList = nlpImplTorForex.minMaxValues(nlpImplTorForex.tokenize(fullArticle));
                list.addAll(allList);
            }
            if (list.size() == 1) {
                forecast.setMin(list.get(0));
                forecast.setMax(list.get(0));
            } else if (list.size() != 0) {
                Collections.sort(list);
                forecast.setMin(list.get(0));
                forecast.setMax(list.get(1));
            } else {
                forecast.setName("trash");
            }
            forecast.setDirection(nlpImplTorForex.getForecastDirection(tokens, forecast.getDirection()));
            if (forecast.getDirection() == '-') {
                String full = document.select("article").text();
                forecast.setDirection(nlpImplTorForex.getForecastDirection(nlpImplTorForex.tokenize(full), forecast.getDirection()));
            }
        } catch (HttpStatusException e) {
            System.out.println("Invalid website");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return forecast;
    }
}
