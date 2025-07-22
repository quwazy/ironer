package ironer.model.irons;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ironer.model.Core;
import ironer.model.enums.IronShape;
import ironer.model.enums.IronType;
import javafx.scene.control.TableColumn;

@Data
@EqualsAndHashCode(callSuper = true)
public class Uzengije extends Iron{
    private double perMeter;    //tezina po metru
    private double length;      //ukupna duzina
    private int amount;         //ukupna kolicina
    private double weight;      //ukupna kilaza
    private double a1;          //jedna stranica
    private double a2;          //druga stranica
    private boolean torziona;   //da li je torziona

    public Uzengije(IronType ironType, double a1, double a2, int amount, boolean torziona) {
        super(IronShape.UZENGIJE, ironType);
        this.perMeter = Core.getWeight(IronType.valueOf(ironType.name()));
        if (a2 == 0.0){
            this.a1 = a1;
            this.a2 = a1;
        } else {
            this.a1 = a1;
            this.a2 = a2;
        }
        if (torziona) {
            this.length = Core.roundNumber(3*this.a1 + 2*this.a2 + 0.2);
            this.torziona = true;
        } else {
            this.length = Core.roundNumber(2*this.a1 + 2*this.a2 + 0.2);
            this.torziona = false;
        }
        this.amount = amount;
        this.weight = Core.roundNumber(this.length * perMeter * this.amount);
    }

    @Override
    public TableColumn<Void, Void> getDraw() {
        return null;
    }
}
