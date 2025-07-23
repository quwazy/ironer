package ironer.model.irons;

import javafx.scene.shape.Shape;
import javafx.scene.Group;
import javafx.scene.shape.Path;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.ArcTo;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ironer.model.Core;
import ironer.model.enums.IronShape;
import ironer.model.enums.IronType;

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
    public Shape getDraw() {
        // Create the path (which is a Shape)
        Path path = new Path();
        path.setStroke(Color.BLACK);
        path.setStrokeWidth(3);
        path.setFill(null);

        // Size and corner calculations
        double size = 45;
        double cornerSize = 10;

        // Starting point (top-left after corner)
        path.getElements().addAll(
                // Move to start position
                new MoveTo(cornerSize, 0),
                // Top edge
                new LineTo(size - cornerSize, 0),
                // Top-right corner
                new ArcTo(cornerSize, cornerSize, 0, size, cornerSize, false, true),
                // Right edge
                new LineTo(size, size - cornerSize),
                // Bottom-right corner
                new ArcTo(cornerSize, cornerSize, 0, size - cornerSize, size, false, true),
                // Bottom edge
                new LineTo(cornerSize, size),
                // Bottom-left corner
                new ArcTo(cornerSize, cornerSize, 0, 0, size - cornerSize, false, true),
                // Left edge
                new LineTo(0, cornerSize),
                // Top-left corner
                new ArcTo(cornerSize, cornerSize, 0, cornerSize, 0, false, true)
        );

        // Add the measurements as part of the path
        // Left side measurement (a1)
        path.getElements().addAll(
                new MoveTo(-10, size/2),
                new LineTo(-5, size/2),
                new LineTo(-5, size/2 + 20),
                new LineTo(-10, size/2 + 20)
        );

        // Bottom measurement (a2)
        path.getElements().addAll(
                new MoveTo(size/2, size + 10),
                new LineTo(size/2, size + 5),
                new LineTo(size/2 + 20, size + 5),
                new LineTo(size/2 + 20, size + 10)
        );

        // Center the shape
        path.setTranslateX(-size/2);
        path.setTranslateY(-size/2 + 25);

        return path;
    }
}
