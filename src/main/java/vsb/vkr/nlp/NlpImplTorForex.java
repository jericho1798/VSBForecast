package vsb.vkr.nlp;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NlpImplTorForex implements Nlp {
    @Override
    public String getForecastDate(String data) {
        String date = "";
        Pattern patternDate = Pattern.compile("(\\d{1,2}(?:\\.|\\s|/)(?:(0?\\d)|(1[0-2])|декабря|января|февраля|марта|апреля|мая|июня|июля|августа|сентября|октября|ноября)(?:\\.|\\s|/)\\d{2,4})");
        Matcher matcherDate = patternDate.matcher(data);
        while (matcherDate.find()) {
            date = data.substring(matcherDate.start(), matcherDate.end());
        }
        return date.trim();
    }

    @Override
    public String getPairName(String data) {
        String name = " ";
        //Pattern pattern = Pattern.compile("((?:EUR|ЕВРО|USD|ДОЛЛАР|GBP|ФУНТ|AUD|АВСТРАЛИЙСКИЙ\\sДОЛЛАР|NZD|НОВОЗЕЛАНДСКИЙ\\sДОЛЛАР)(?:/|\\s|\\-|\\s\\-\\s)(?:USD|ДОЛЛАР|JPY|ИЕНА|ЙЕНА|CHF|ФРАНК|CAD|КАНАДСКИЙ\\sДОЛЛАР))");
        Pattern pattern = Pattern.compile("(?:EUR|USD|GBP|AUD|NZD)(?:/|\\s|-|\\s-\\s|\\sК\\s)(?:USD|JPY|CHF|CAD)");
        Matcher matcher = pattern.matcher(data);
        while (matcher.find()) {
            name = data.substring(matcher.start(), matcher.end());
        }
        if (name.equals(" ")) {
            pattern = Pattern.compile("(?:ЕВРО|EUR|USD|ДОЛЛАРА?|GBP|ФУНТА?|AUD|(АВСТРАЛИЙСК(?:ИЙ|ОГО)\\sДОЛЛАРА?)|NZD|(НОВОЗЕЛАНДСК(?:ИЙ|ОГО)\\sДОЛЛАРА?))(?:/|\\s|-|\\s-\\s|\\sК\\s)(?:USD|ДОЛЛАРУ?|JPY|[ИЙ]ЕН[АЕ]|ЙЕНА|CHF|ФРАНКУ?|CAD|(КАНАДСК(?:ИЙ|ОМУ)\\sДОЛЛАРУ?))");
            matcher = pattern.matcher(data);
            while (matcher.find()) {
                name = data.substring(matcher.start(), matcher.end());
            }
        }
        return name;
    }

    @Override
    public String[] tokenize(String text) {
        return text.split("((?:\\s|\\.\\s)|\\.$|\\,\\s)");
    }

    @Override
    public char getForecastDirection(String[] tokens, char curDir) {
        char direction = curDir;
        for (String token : tokens) {
            if (direction == '-') {
                if (token.matches("((?:низ|ниже|вниз|падения|падение|крах|обвал|обвала|краха|снижения|снижение))")) {
                    direction = 'd';
                    break;
                }
                if (token.matches("((?:вверх|верх|рост|увеличение|подъем|пробой|подъём|подъёма|подъема|выше|над|роста))")) {
                    direction = 'u';
                    break;
                }
            }
        }
        return direction;
    }

    @Override
    public List<Float> minMaxValues(String[] tokens) {
        List<Float> values = new ArrayList<>();
        for (String token : tokens) {
            if (token.matches("(\\d+(?:\\.|\\,)\\d+)")) {
                if (values.size() < 2) {
                    values.add(Float.parseFloat(token));
                }
            }
        }
        return values;
    }
}
