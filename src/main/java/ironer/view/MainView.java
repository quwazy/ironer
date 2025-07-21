package ironer.view;

import ironer.model.Iron;
import ironer.model.enums.IronShape;
import ironer.model.enums.IronType;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MainView extends Stage {
    private VBox mainBox;
    private List<Iron> ironList;
    /// info row
    private Label identifierLabel = new Label();
    private Label ordererLabel = new Label();
    private Label dateLabel = new Label();
    private TextField identifierTextField = new TextField();
    private TextField ordererTextField = new TextField();
    private TextField dateTextField = new TextField();
    /// add row
    private ComboBox<String> ironShapeComboBox = new ComboBox<>();
    private ComboBox<String> ironTypeComboBox = new ComboBox<>();
    private HBox dynamicHBox = new HBox();
    private CheckBox torzionaCheckBox = new CheckBox();
    private TextField a1TextField = new TextField();
    private TextField a2TextField = new TextField();
    private TextField amountTextField = new TextField();
    private Button addButton = new Button("Dodaj");
    /// preview
    private ListView<Iron> listView = new ListView<>();

    public MainView() {
        this.mainBox = new VBox(20);
        this.ironList = new ArrayList<>();

        initInfoRow();
        initAddRow();
        initPreview();

        Scene scene = new Scene(mainBox, 700, 700);
        this.setScene(scene);
    }

    private void initInfoRow(){
        this.identifierLabel.setText("Identifier:");
        this.identifierTextField.setPromptText("oznaka");
        this.identifierTextField.setText("RN 12345");

        this.ordererLabel.setText("Orderer:");
        this.ordererTextField.setPromptText("narucilac");
        this.ordererTextField.setText("unknown");

        this.dateLabel.setText("Date:");
        this.dateTextField.setPromptText("datum");
        this.dateTextField.setText(LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));

        HBox hBox = new HBox(20);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(identifierLabel, identifierTextField, ordererLabel, ordererTextField, dateLabel, dateTextField);

        this.mainBox.getChildren().addAll(hBox);
    }

    private void initAddRow(){
        for (IronShape ironShape : IronShape.values()) {
            this.ironShapeComboBox.getItems().add(ironShape.name());
        }
        this.ironShapeComboBox.setValue(IronShape.SIPKE.name());
        initAddSipke();
        this.ironShapeComboBox.setOnAction(event -> {
            this.dynamicHBox.getChildren().clear();
            if (IronShape.SIPKE.name().equals(this.ironShapeComboBox.getValue())) {
                initAddSipke();
            }
            else if (IronShape.UZENGIJE.name().equals(this.ironShapeComboBox.getValue())) {
                initAddUzengije();
            }
        });

        for (IronType ironType : IronType.values()) {
            this.ironTypeComboBox.getItems().add(ironType.name());
        }
        this.ironTypeComboBox.setValue(IronType.R8.name());



        HBox hBox = new HBox(20);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(ironShapeComboBox, ironTypeComboBox, dynamicHBox, addButton);

        this.mainBox.getChildren().addAll(hBox);
    }

    private void initAddSipke(){
        this.a1TextField.setPromptText("duzina (m)");
        this.amountTextField.setPromptText("kolicina (kom)");
        this.addButton.setOnAction(event -> {
            Iron iron = new Iron(IronType.valueOf(this.ironTypeComboBox.getValue()), Double.parseDouble(a1TextField.getText()), Integer.parseInt(this.amountTextField.getText()));
            System.out.println(iron.toString());
            ironList.add(iron);
            listView.getItems().add(iron);
        });
        addButton.setDefaultButton(true);
        this.dynamicHBox.getChildren().addAll(a1TextField, amountTextField, addButton);
    }

    private void initAddUzengije(){
        this.a1TextField.setPromptText("a1 duzina (m)");
        this.a2TextField.setPromptText("a2 duzina (m)");
        this.amountTextField.setPromptText("kolicina (kom)");
        this.addButton.setOnAction(event -> {
            Iron iron = new Iron(
                    IronType.valueOf(ironTypeComboBox.getValue()),
                    Double.parseDouble(a1TextField.getText()),
                    Double.parseDouble(a2TextField.getText()),
                    Integer.parseInt(this.amountTextField.getText()),
                    this.torzionaCheckBox.isSelected()
            );
            ironList.add(iron);
            listView.getItems().add(iron);
            System.out.println(iron.toString());
        });
        addButton.setDefaultButton(true);

        this.dynamicHBox.getChildren().addAll(torzionaCheckBox, a1TextField, a2TextField, amountTextField, addButton);
    }

    private void initPreview(){
        this.mainBox.getChildren().add(listView);
    }
}
