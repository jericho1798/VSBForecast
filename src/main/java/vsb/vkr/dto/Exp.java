package vsb.vkr.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;

@Data
@NoArgsConstructor()
public class Exp {
 //   @Id
    private String name;        //Наименование эксперта
    private int num;            //Обще число прогнозов эксперта
    private int numB;           //Число неверных прогнозов эксперта
    private int numG;           //Число неубыточных прогнозов эксперта
    private int numE;           //Число верных прогнозов эксперта
    private double rate;        //Рейтинг эксперта

    public Exp(String i, int numberOfForecasts, int numB, int numG, int numE) {
        this.name = i;
        this.num = numberOfForecasts;
        this.numB = numB;
        this.numG = numG;
        this.numE = numE;
        this.rate = expertRate(this.num, this.numB, this.numG, this.numE);
    }

    public double expertRate(int numberOfForecasts, int numB, int numG, int numE) {
        double out = 0;
        out += numG;
        out += numE;
        out /= numberOfForecasts;
        out = (int) (out * 100);
        out /= 100;
        return out;
    }
}