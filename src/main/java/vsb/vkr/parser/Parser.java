package vsb.vkr.parser;

import vsb.vkr.dto.Fresh;

import java.util.List;

public interface Parser {
    /**
     * @return Поиск ссылок на тексты рекомендаций экспертов
     */
    List<Fresh> parseArticlesLink(String baseUrl, boolean allPageScan);

    /**
     * @return Запуск процесса извлечения данных
     */
    Fresh parseData(String url);
}
