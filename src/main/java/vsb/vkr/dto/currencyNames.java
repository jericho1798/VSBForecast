package vsb.vkr.dto;

public enum currencyNames {
    EURUSD("EUR/USD"),
    USDJPY("USD/JPY"),
    GBPUSD("GBP/USD"),
    AUDUSD("AUD/USD"),
    USDCHF("USD/CHF"),
    NZDUSD("NZD/USD"),
    USDCAD("USD/CAD");

    private final String name;

    @Override
    public String toString() {
        return name;
    }

    currencyNames(String name) {
        this.name = name;
    }
}
