package ironer.model.irons;

import ironer.model.Core;
import ironer.model.enums.IronShape;
import ironer.model.enums.IronType;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Mreze extends Iron{
    private int amount;
    private double weight;

    public Mreze(IronType ironType, int amount) {
        super(IronShape.MREZE, ironType);
        this.amount = amount;
        this.weight = Core.getRoundNumber(amount * Core.getWeight(IronType.valueOf(ironType.name())));
    }

//    @Override
//    public Group getDraw() {
//        Group group = new Group();
//
//        Circle circle = new Circle(0, 0, 0.1);
//        circle.setStrokeWidth(0.1);
//        circle.setFill(null);
//        circle.setStroke(Color.BLACK);
//
//        group.getChildren().addAll(circle);
//        return group;
//    }
    @Override
    public Group getDraw() {
        Group g = new Group();

        double size   = 60;            // overall grid size
        double cell   = size / 3.0;    // cell width/height
        double stroke = 1.5;           // line thickness

        // Vertical lines
        javafx.scene.shape.Line v1 = new javafx.scene.shape.Line(cell, 0, cell, size);
        javafx.scene.shape.Line v2 = new javafx.scene.shape.Line(2 * cell, 0, 2 * cell, size);

        // Horizontal lines
        javafx.scene.shape.Line h1 = new javafx.scene.shape.Line(0, cell, size, cell);
        javafx.scene.shape.Line h2 = new javafx.scene.shape.Line(0, 2 * cell, size, 2 * cell);

        for (javafx.scene.shape.Line ln : new javafx.scene.shape.Line[]{v1, v2, h1, h2}) {
            ln.setStroke(javafx.scene.paint.Color.BLACK);
            ln.setStrokeWidth(stroke);
            ln.setStrokeLineCap(javafx.scene.shape.StrokeLineCap.ROUND);
        }

        g.getChildren().addAll(v1, v2, h1, h2);
        return g;
    }
}
