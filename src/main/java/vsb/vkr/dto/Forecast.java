package vsb.vkr.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Entity
public class Forecast {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String link;
    private String date;
    private String currencyPairName;
    private float min;
    private float max;
    private char direction;
    private float realValue = -1;
    private float rate;

    public Forecast(String name, String link, String date, String currencyPairName, float min, float max, char direction) {
        this.name = name;
        this.link = link;
        this.date = date;
        this.currencyPairName = currencyPairName;
        this.min = min;
        this.max = max;
        this.direction = direction;
    }
}



