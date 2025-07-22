package ironer.view;

import ironer.model.enums.IronShape;
import ironer.model.enums.IronType;
import ironer.model.irons.Iron;
import ironer.model.irons.Sipke;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDateTime;

public class OrderView extends Stage {
    private VBox mainBox;
    private ObservableList<Iron> ironObservableList;
    /// info row
    private Label identifierLabel = new Label();
    private TextField identifierTextField = new TextField();
    private Label ordererLabel = new Label();
    private TextField ordererTextField = new TextField();
    private Label dateLabel = new Label();
    private TextField dateTextField = new TextField();
    /// add row
    private ComboBox<String> ironShapeComboBox = new ComboBox<>();
    private ComboBox<String> ironTypeComboBox = new ComboBox<>();
    private HBox dynamicHBox = new HBox(10);
    private CheckBox torzionaCheckBox = new CheckBox();
    private TextField a1TextField = new TextField();
    private TextField a2TextField = new TextField();
    private TextField amountTextField = new TextField();
    private Button addButton = new Button("Dodaj");
    /// preview row
    private TableView tableView = new TableView<>();
    private Button removeButton = new Button("Ukloni");
    /// final row
    private Label totalG = new Label();
    private TextField totalGTextField = new TextField();
    private Label totalR = new Label();
    private TextField totalRTextField = new TextField();
    private Label totalV = new Label();
    private TextField totalVTextField = new TextField();

    public OrderView(){
        this.mainBox = new VBox(20);
        this.ironObservableList = FXCollections.observableArrayList();

        initInfoRow();
        initAddRow();
        initTable();
//        initFinal();

        Scene scene = new Scene(mainBox, 850, 700);
        this.setScene(scene);
    }

    private void initInfoRow(){
        this.identifierLabel.setText("Oznaka:");
        this.identifierTextField.setPromptText("oznaka");
        this.identifierTextField.setText("RN 12345");

        this.ordererLabel.setText("Narucilac:");
        this.ordererTextField.setPromptText("narucilac");
        this.ordererTextField.setText("unknown");

        this.dateLabel.setText("Datum:");
        this.dateTextField.setPromptText("datum");
        this.dateTextField.setText(LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));

        HBox hBox = new HBox(20);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(identifierLabel, identifierTextField, ordererLabel, ordererTextField, dateLabel, dateTextField);

        this.mainBox.getChildren().addAll(hBox);
    }

    private void initAddRow(){
        //sipke,uzengije,stubovi
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
            else if (IronShape.STUBOVI.name().equals(this.ironShapeComboBox.getValue())) {

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

    private void initTable(){}

    private void initAddSipke(){
        this.ironTypeComboBox.getItems().clear();
        this.ironTypeComboBox.getItems().addAll("R6", "R8", "R10", "R12", "R14", "R16");
        this.ironTypeComboBox.setValue("R6");

        this.a1TextField.setPromptText("duzina (m)");
        this.a1TextField.setMinWidth(55);
        this.a1TextField.setMaxWidth(55);
        Label meterLabel = new Label("metara");

        this.amountTextField.setPromptText("kolicina (kom)");
        this.amountTextField.setMinWidth(55);
        this.amountTextField.setMaxWidth(55);
        Label kolicinaLabel = new Label("kom");

        this.addButton.setOnAction(event -> {
            Sipke sipke = new Sipke(IronType.valueOf(ironTypeComboBox.getValue()), Double.parseDouble(a1TextField.getText()), Integer.parseInt(amountTextField.getText()));
            ironObservableList.add(sipke);
            a1TextField.clear();
            amountTextField.clear();
        });

        addButton.setDefaultButton(true);
        dynamicHBox.setAlignment(Pos.CENTER);
        dynamicHBox.getChildren().addAll(a1TextField, meterLabel, amountTextField, kolicinaLabel, addButton);
    }

    private void initAddUzengije(){
        this.ironTypeComboBox.getItems().clear();
        this.ironTypeComboBox.getItems().addAll("G6", "G8", "R8");
        this.ironTypeComboBox.setValue("G6");

        this.a1TextField.setPromptText("a1 duzina (m)");
        this.a2TextField.setPromptText("a2 duzina (m)");
        this.amountTextField.setPromptText("kolicina (kom)");
    }
}
