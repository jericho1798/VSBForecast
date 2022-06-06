package vsb.vkr.parser;

import vsb.vkr.dto.Fresh;

import java.util.List;

public interface Parser {
    List<Fresh> parseArticlesLink(String baseUrl, boolean allPageScan);

    Fresh parseData(String url);
}
