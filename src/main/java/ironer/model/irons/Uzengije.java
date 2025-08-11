package ironer.model.irons;

import javafx.scene.shape.*;
import javafx.scene.Group;
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
            this.length = Core.getRoundNumber(3*this.a1 + 2*this.a2 + 0.2);
            this.torziona = true;
        } else {
            this.length = Core.getRoundNumber(2*this.a1 + 2*this.a2 + 0.2);
            this.torziona = false;
        }
        this.amount = amount;
        this.weight = Core.getRoundNumber(this.length * perMeter * this.amount);
    }

    @Override
    public Group getDraw() {
        Group group = new Group();

        // Create the square with rounded corners
        Path path = new Path();
        path.setStroke(Color.BLACK);
        path.setStrokeWidth(3);
        path.setFill(null);

        // Size and corner calculations
        double size = 50;
        double cornerSize = 8;

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

        // First diagonal line
        Line diagonalLine1 = new Line();
        diagonalLine1.setStartX(size);  // Top-right X
        diagonalLine1.setStartY(cornerSize);  // Top-right Y (accounting for corner)
        diagonalLine1.setEndX(size - (size - (size/2 + 7))/2);  // Halfway to center X
        diagonalLine1.setEndY(cornerSize + (size/2 + 10 - cornerSize)/2);  // Halfway to center Y
        diagonalLine1.setStroke(Color.BLACK);
        diagonalLine1.setStrokeWidth(1.5);

        // Second diagonal line
        Line diagonalLine2 = new Line();
        double offsetY = -5;
        diagonalLine2.setStartX(size);  // Same X start as line 1
        diagonalLine2.setStartY(cornerSize + offsetY + 1);  // Start 5px above line 1
        diagonalLine2.setEndX(size - (size - (size/2 + 7))/2);  // Same X end as line 1
        diagonalLine2.setEndY(cornerSize + (size/2 + 10 - cornerSize)/2 + offsetY);  // End 5px above line 1
        diagonalLine2.setStroke(Color.BLACK);
        diagonalLine2.setStrokeWidth(1.5);

        // Parallel line for torziona filed
        Line parallelLeftLine = new Line();
        parallelLeftLine.setStartX(cornerSize);  // Parallel line starts offset from the left edge
        parallelLeftLine.setStartY(cornerSize + 40);  // Align parallel to the left corner
        parallelLeftLine.setEndX(cornerSize);  // End X remains the same for vertical line
        parallelLeftLine.setEndY(size - cornerSize - 40);  // End position slightly above bottom
        parallelLeftLine.setStroke(Color.BLACK);  // For visibility, set the color to red (change as desired)
        parallelLeftLine.setStrokeWidth(2);  // Visibility width for the additional line

        // Add text for a1 (left side)
        Text a1Text = new Text(String.format("%.0f", a1*100));
        a1Text.setFont(Font.font(12));
        a1Text.setRotate(-90);
        a1Text.setX(-18);
        a1Text.setY(size/2 + 5);

        // Add text for a2 (bottom)
        Text a2Text = new Text(String.format("%.0f", a2*100));
        a2Text.setFont(Font.font(12));
        a2Text.setX(size/2 - 5);
        a2Text.setY(size + 12);

        if (this.torziona){
            group.getChildren().addAll(path, parallelLeftLine, diagonalLine1, diagonalLine2, a1Text, a2Text);
        }else {
            group.getChildren().addAll(path, diagonalLine1, diagonalLine2, a1Text, a2Text);
        }
        group.setTranslateX(-size/2);
        group.setTranslateY(-size/2 + 25);
        return group;
    }
}
