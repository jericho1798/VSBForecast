package vsb.vkr.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
//@Entity
public class Fresh {
  //  @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String link;
    private String date;
    private String currencyPairName;
    private float min;
    private float max;
    private char direction = '-';

    public void setDate(String date) {
        if (date.equals("")) {
            this.date = "trash";
        } else {
            String[] s = date.split("(?:\\.|\\s|/)");
            if (s[0].length() == 1) s[0] = "0".concat(s[0]);
            if (s[1].matches("\\d\\d?")) {
                if (s[1].length() == 1) s[1] = "0".concat(s[1]);
            } else {
                switch (s[1]) {
                    case "января":
                        s[1] = "01";
                        break;
                    case "февраля":
                        s[1] = "02";
                        break;
                    case "марта":
                        s[1] = "03";
                        break;
                    case "апреля":
                        s[1] = "04";
                        break;
                    case "мая":
                        s[1] = "05";
                        break;
                    case "июня":
                        s[1] = "06";
                        break;
                    case "июля":
                        s[1] = "07";
                        break;
                    case "августа":
                        s[1] = "08";
                        break;
                    case "сентября":
                        s[1] = "09";
                        break;
                    case "октября":
                        s[1] = "10";
                        break;
                    case "ноября":
                        s[1] = "11";
                        break;
                    case "декабря":
                        s[1] = "12";
                        break;
                }
            }
            if (s[2].length() == 2) s[2] = "20".concat(s[2]);
            this.date = String.join(".", s);
        }
    }

    public void setCurrencyPairName(String currencyPairName) {
        if (currencyPairName.matches("(\\w{3}/\\w{3})")) {
            this.currencyPairName = currencyPairName;
        } else {
            if (currencyPairName.matches("(?:ЕВРО|EUR)(?:/|\\s|\\-|\\s\\-\\s|\\sК\\s)(?:USD|ДОЛЛАРУ?)")) {
                this.currencyPairName = "EUR/USD";
            } else if (currencyPairName.matches("(?:USD|ДОЛЛАРА?)(?:/|\\s|\\-|\\s\\-\\s|\\sК\\s)(?:JPY|(?:И|Й)ЕН(?:А|Е)|ЙЕНА)")) {
                this.currencyPairName = "USD/JPY";
            } else if (currencyPairName.matches("(?:GBP|ФУНТА?)(?:/|\\s|\\-|\\s\\-\\s|\\sК\\s)(?:USD|ДОЛЛАРУ?)")) {
                this.currencyPairName = "GBP/USD";
            } else if (currencyPairName.matches("(?:AUD|(АВСТРАЛИЙСК(?:ИЙ|ОГО)\\sДОЛЛАРА?))(?:/|\\s|\\-|\\s\\-\\s|\\sК\\s)(?:USD|ДОЛЛАРУ?)")) {
                this.currencyPairName = "AUD/USD";
            } else if (currencyPairName.matches("(?:USD|ДОЛЛАРА?)(?:/|\\s|\\-|\\s\\-\\s|\\sК\\s)(?:CHF|ФРАНКУ?)")) {
                this.currencyPairName = "USD/CHF";
            } else if (currencyPairName.matches("(?:NZD|(НОВОЗЕЛАНДСК(?:ИЙ|ОГО)\\sДОЛЛАРА?))(?:/|\\s|\\-|\\s\\-\\s|\\sК\\s)(?:USD|ДОЛЛАРУ?)")) {
                this.currencyPairName = "NZD/USD";
            } else if (currencyPairName.matches("(?:USD|ДОЛЛАРА?)(?:/|\\s|\\-|\\s\\-\\s|\\sК\\s)(?:CAD|(КАНАДСК(?:ИЙ|ОМУ)\\sДОЛЛАРУ?))")) {
                this.currencyPairName = "USD/CAD";
            } else {
                this.currencyPairName = currencyPairName;
            }
        }
    }

    @Override
    public String toString() {
        return "Forecast{" +
                "name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", date='" + date + '\'' +
                ", currencyPairName='" + currencyPairName + '\'' +
                ", min=" + min +
                ", max=" + max +
                ", direction=" + direction +
                '}';
    }
}
