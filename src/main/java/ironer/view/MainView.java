package ironer.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.time.LocalDateTime;

public class MainView extends Stage {
    private HBox mainHBox;
    /// info row
    private Label identifierLabel;
    private Label ordererLabel;
    private Label dateLabel;
    private TextField identifierTextField = new TextField();
    private TextField ordererTextField = new TextField();
    private TextField dateTextField = new TextField();

    public MainView() {
        this.mainHBox = new HBox(20);
        initInfoRow();

        Scene scene = new Scene(this.mainHBox, 700, 700);
        this.setScene(scene);
    }

    private void initInfoRow(){
        this.identifierLabel = new Label("Identifier:");
        this.identifierTextField.setPromptText("oznaka");
        this.identifierTextField.setText("RN 12345");

        this.ordererLabel = new Label("Orderer:");
        this.ordererTextField.setPromptText("narucilac");
        this.ordererTextField.setText("unknown");

        this.dateLabel = new Label("Date:");
        this.dateTextField.setPromptText("datum");
        this.dateTextField.setText(LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));

        HBox hBox = new HBox(20);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(identifierLabel, identifierTextField, ordererLabel, ordererTextField, dateLabel, dateTextField);

        this.mainHBox.getChildren().addAll(hBox);
    }
}
