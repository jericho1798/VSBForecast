package vsb.vkr.nlp;

import java.util.List;

public interface Nlp {
    String getForecastDate(String data);

    String getPairName(String data);

    String[] tokenize(String text);

    char getForecastDirection(String[] tokens, char curDir);

    List<Float> minMaxValues(String[] tokens);
}
