package ironer.model.irons;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ironer.model.Core;
import ironer.model.enums.IronShape;
import ironer.model.enums.IronType;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.StackPane;
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
        this.weight = Math.round(this.length * perMeter * this.amount * 100.0) / 100.0;
    }

    @Override
    public TableColumn<Void, Void> getDraw() {
        TableColumn<Void, Void> drawColumn = new TableColumn<>("Skica");
        drawColumn.setMinWidth(250);
        drawColumn.setMaxWidth(400);

        drawColumn.setCellFactory(col -> new TableCell<>() {
            private final Line line = new Line(0, 0, 50, 0);
            {
                line.setStrokeWidth(3);
                line.setStroke(Color.BLACK);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    StackPane pane = new StackPane(line);
                    pane.setAlignment(Pos.CENTER);
                    setGraphic(pane);
                }
            }
        });

        return drawColumn;
    }
}
