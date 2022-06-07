package vsb.vkr.nlp;

import java.util.List;

public interface Nlp {
    /**
     * @return Получение даты прогноза
     */
    String getForecastDate(String data);

    /**
     * @return Получение наименование валютной пары
     */
    String getPairName(String data);

    /**
     * @return Токенизация
     */
    String[] tokenize(String text);

    /**
     * @return Получение направелния изменения котировок
     */
    char getForecastDirection(String[] tokens, char curDir);

    /**
     * @return Получение минимального и максимальногшо значений
     */
    List<Float> minMaxValues(String[] tokens);
}
