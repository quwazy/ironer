package ironer.model.irons;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
    public Group getDraw() {
        Group group = new Group();

        // Create the line
        Line line = new Line(0, 0, 120, 0);
        line.setStrokeWidth(3);
        line.setStroke(Color.BLACK);

        // Create text for length
        Text lengthText = new Text(String.format("%.2f", length));
        lengthText.setFont(Font.font(12));
        // Position text above the middle of the line
        lengthText.setX(40);  // 100/2 - approximate text width/2
        lengthText.setY(-5);  // Position above the line

        // Add both elements to the group
        group.getChildren().addAll(line, lengthText);

        return group;
    }
}
