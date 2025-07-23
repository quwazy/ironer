package ironer.model.irons;

import javafx.scene.shape.Shape;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ironer.model.enums.IronShape;
import ironer.model.enums.IronType;

@Data
@EqualsAndHashCode(callSuper = true)
public class Stuobovi extends Iron{
    private double length;      //ukupna duzina
    private int amount;             //ukupna kolicina
    private double weight;      //ukupna kilaza
    private int uzengijePerMeter;   //koliko uzengija ide po metru u stub
    private Sipke sipke;            //sipka za stubove
    private Uzengije uzengije;      //uzengija za stubove

    public Stuobovi(IronType ironType, int amount, int uzengijePerMeter, double length, double a1, double a2){
        super(IronShape.STUBOVI, ironType);
        this.amount = amount;
        this.uzengijePerMeter = uzengijePerMeter;
        this.length = length;
        this.sipke = new Sipke(ironType, length, this.amount * 4);
        this.uzengije = new Uzengije(IronType.G6, a1, a2,this.amount * ((int)
                Math.ceil(this.uzengijePerMeter * length)),false);
        this.weight = sipke.getWeight() + uzengije.getWeight();
    }

    @Override
    public Shape getDraw() {
        return null;
    }
}
