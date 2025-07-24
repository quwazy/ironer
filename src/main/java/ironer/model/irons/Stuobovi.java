package ironer.model.irons;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
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

        // Create the square with rounded corners
        Path path = new Path();
        path.setStroke(Color.BLACK);
        path.setStrokeWidth(2);
        path.setFill(null);

        // Size and corner calculations
        double size = 50;
        double cornerSize = 5;

        // Draw the square with rounded corners
        path.getElements().addAll(
                new MoveTo(cornerSize, 0),
                new LineTo(size - cornerSize, 0),
                new ArcTo(cornerSize, cornerSize, 0, size, cornerSize, false, true),
                new LineTo(size, size - cornerSize),
                new ArcTo(cornerSize, cornerSize, 0, size - cornerSize, size, false, true),
                new LineTo(cornerSize, size),
                new ArcTo(cornerSize, cornerSize, 0, 0, size - cornerSize, false, true),
                new LineTo(0, cornerSize),
                new ArcTo(cornerSize, cornerSize, 0, cornerSize, 0, false, true)
        );

        // Add circles to each corner
        Circle topLeftCircle = new Circle(cornerSize, cornerSize, 4);
//        topLeftCircle.setFill(Color.BLACK);

        Circle topRightCircle = new Circle(size - cornerSize, cornerSize, 4);
//        topRightCircle.setFill(Color.BLACK);

        Circle bottomLeftCircle = new Circle(cornerSize, size - cornerSize, 4);
//        bottomLeftCircle.setFill(Color.BLACK);

        Circle bottomRightCircle = new Circle(size - cornerSize, size - cornerSize, 4);
//        bottomRightCircle.setFill(Color.BLACK);

        // Add text for a1 (left side)
        Text a1Text = new Text(String.format("%.0f", this.uzengije.getA1() * 100));
        a1Text.setFont(Font.font(12));
        a1Text.setRotate(-90);
        a1Text.setX(-18);
        a1Text.setY(size / 2 + 5);

        // Add text for a2 (bottom)
        Text a2Text = new Text(String.format("%.0f", this.uzengije.getA2() * 100));
        a2Text.setFont(Font.font(12));
        a2Text.setX(size / 2 - 7);
        a2Text.setY(size + 12);

        Text sipkeText = new Text("ukupno sipki: " + this.sipke.getAmount());
        sipkeText.setFont(Font.font(12));
        sipkeText.setX(size + 20);
        sipkeText.setY(size - 33);

        Text uzengijeText = new Text("ukupno uzengija: " + this.uzengije.getAmount());
        uzengijeText.setFont(Font.font(12));
        uzengijeText.setX(size + 20);
        uzengijeText.setY(size - 20);

        Text uzengijePerMeterText = new Text("uzengije po metru: " + this.uzengijePerMeter);
        uzengijePerMeterText.setFont(Font.font(12));
        uzengijePerMeterText.setX(size + 20);
        uzengijePerMeterText.setY(size - 7);

        // Add all elements to the group
        group.getChildren().addAll(path, topLeftCircle, topRightCircle, bottomLeftCircle, bottomRightCircle, a1Text, a2Text, sipkeText, uzengijeText, uzengijePerMeterText);

        // Center the group
        group.setTranslateX(-size / 2);
        group.setTranslateY(-size / 2 + 25);

        return group;
    }
}
