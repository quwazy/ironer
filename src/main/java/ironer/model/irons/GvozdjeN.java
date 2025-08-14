package ironer.model.irons;

import ironer.model.Core;
import ironer.model.enums.IronShape;
import ironer.model.enums.IronType;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GvozdjeN extends Iron{
    private IronShape realIronShape;
    private double perMeter;
    private double length;
    private int amount;
    private double weight;

    public GvozdjeN(IronShape ironShape, IronType ironType, double length, int amount) {
        super(IronShape.GVOZDJE_N, ironType);
        this.realIronShape = ironShape;
        this.perMeter = Core.getWeight(IronType.valueOf(ironType.name()));
        this.length = length;
        this.amount = amount;
        this.weight = Core.getRoundNumber(this.length * perMeter * this.amount);
    }

    @Override
    public Group getDraw() {
        Group group = new Group();

        Circle circle = new Circle(0, 0, 0.5);
        circle.setStrokeWidth(0.5);
        circle.setFill(null);
        circle.setStroke(Color.BLACK);

        group.getChildren().addAll(circle);
        return group;
    }
}
