package ironer.model;

import lombok.Data;
import ironer.model.enums.IronShape;
import ironer.model.enums.IronType;

import java.util.Objects;

@Data
public class Iron {
    private IronShape ironShape;//oblik gvozdja
    private IronType ironType;  //tezina po metru
    private double perMeter;    //tezina po metru
    private double length;      //ukupna duzina
    private int amount;         //ukupna kolicina
    private double weight;      //ukupna kilaza

    /// konstruktor za sipke
    public Iron(IronType ironType, double length, int amount) {
        this.ironShape = IronShape.SIPKE;
        this.ironType = ironType;
        this.perMeter = Core.getWeight(IronType.valueOf(ironType.name()));
        this.length = length;
        this.amount = amount;
        this.weight = Math.round(this.length * perMeter * this.amount * 100.0) / 100.0;
    }

    /// konstruktor za uzengije
    public Iron(IronType ironType, double a1, double a2, int amount, boolean torziona) {
        this.ironShape = IronShape.UZENGIJE;
        this.ironType = ironType;
        this.perMeter = Core.getWeight(IronType.valueOf(ironType.name()));
        if (torziona) {
            this.length = Math.round((3*a1 + 2*a2 + 0.2) * 100.0) / 100.0;
        } else {
            this.length = 2*a1 + 2*a2 + 0.2;
        }
        this.amount = amount;
        this.weight = Math.round(this.length * perMeter * this.amount * 100.0) / 100.0;
    }

    @Override
    public String toString() {
        return "Iron{" +
                "ironShape=" + ironShape +
                ", ironType=" + ironType +
                ", perMeter=" + perMeter +
                ", length=" + length +
                ", amount=" + amount +
                ", weight=" + weight +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Iron iron = (Iron) o;
        return Double.compare(perMeter, iron.perMeter) == 0 && Double.compare(length, iron.length) == 0 && amount == iron.amount && Double.compare(weight, iron.weight) == 0 && ironShape == iron.ironShape && ironType == iron.ironType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ironShape, ironType, perMeter, length, amount, weight);
    }
}
