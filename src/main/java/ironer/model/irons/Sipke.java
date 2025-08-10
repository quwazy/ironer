package ironer.model.irons;

import javafx.scene.Group;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ironer.model.Core;
import ironer.model.enums.IronShape;
import ironer.model.enums.IronType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

@Data
@EqualsAndHashCode(callSuper = true)
public class Sipke extends Iron{
    private double perMeter;    //tezina po metru
    private double length;      //ukupna duzina
    private int amount;         //ukupna kolicina
    private double weight;      //ukupna kilaza

    public Sipke(IronType ironType, double length, int amount) {
        super(IronShape.SIPKE, ironType);
        this.perMeter = Core.getWeight(IronType.valueOf(ironType.name()));
        this.length = length;
        this.amount = amount;
        this.weight = Core.getRoundNumber(this.length * perMeter * this.amount);
    }

    @Override
    public Group getDraw() {
        Group group = new Group();

        // Create the line
        Line line = new Line(0, 0, 120, 0);
        line.setStrokeWidth(3);
        line.setStroke(Color.BLACK);

        // Text and text position
        Text lengthText = new Text(String.format("%.2f", length));
        lengthText.setFont(Font.font(12));
        lengthText.setX(40);
        lengthText.setY(-5);

        group.getChildren().addAll(line, lengthText);
        return group;
    }
}
