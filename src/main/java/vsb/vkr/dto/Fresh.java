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
public class Fresh {
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
}
