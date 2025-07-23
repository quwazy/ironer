package ironer.view;

import ironer.model.enums.IronShape;
import ironer.model.enums.IronType;
import ironer.model.irons.Iron;
import ironer.model.irons.Sipke;
import ironer.model.irons.Stuobovi;
import ironer.model.irons.Uzengije;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Shape;
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
    private TextField uzengijePerMeterTextField = new TextField();
    private TextField duzinaStubaTextField = new TextField();
    private TextField a1TextField = new TextField();
    private TextField a2TextField = new TextField();
    private TextField amountTextField = new TextField();
    private Button addButton = new Button("Dodaj");
    /// preview row
    private TableView<Iron> tableView = new TableView<>();
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
        initFinal();

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
                initAddStubove();
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

    private void initTable(){
        TableColumn<Iron, Void> drawColumn = new TableColumn<>("Skica");
        drawColumn.setMinWidth(250);
        drawColumn.setMaxWidth(400);

        drawColumn.setCellFactory(col -> new TableCell<Iron, Void>() {
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    Iron iron = (Iron) getTableView().getItems().get(getIndex());
                    if (iron != null) {
                        Shape shape = iron.getDraw();
                        StackPane pane = new StackPane(shape);
                        pane.setAlignment(Pos.CENTER);
                        setGraphic(pane);
                    } else {
                        setGraphic(null);
                    }
                }
            }
        });


        TableColumn<Iron, IronType> fiColumn  = new TableColumn<>("fi");
        fiColumn.setCellValueFactory(new PropertyValueFactory<>("ironType"));
        TableColumn<Iron, Double> lengthColumn = new TableColumn<>("Duzina");
        lengthColumn.setMaxWidth(Double.MAX_VALUE);
        lengthColumn.setMinWidth(300);
        lengthColumn.setCellValueFactory(new PropertyValueFactory<>("length"));
        TableColumn<Iron, Integer> amountColumn = new TableColumn<Iron, Integer>("Kolicina");
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        TableColumn<Iron, Double> weightColumn = new TableColumn<>("Tezina(kg)");
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));

        tableView.setFixedCellSize(80);
//        tableView.getStylesheets().add(Objects.requireNonNull(getClass().getResource("src/main/resources/style.css")).toExternalForm());
        tableView.setMaxWidth(Double.MAX_VALUE);
        tableView.setStyle("-fx-font-size: 18px;");
        this.tableView.setItems(ironObservableList);
        this.tableView.getColumns().addAll(drawColumn, fiColumn, lengthColumn, amountColumn, weightColumn);

        this.mainBox.getChildren().addAll(this.tableView);
    }

    private void initFinal(){
        this.totalG.setText("Uzengije");
        this.totalGTextField.setText("0.0");
        this.totalR.setText("Sipke");
        this.totalRTextField.setText("0.0");
        this.totalV.setText("Vezano");
        this.totalVTextField.setText("0.0");

        VBox vBoxG = new VBox(5);
        vBoxG.setAlignment(Pos.BOTTOM_LEFT);
        vBoxG.getChildren().addAll(this.totalG, totalGTextField);

        VBox vBoxR = new VBox(5);
        vBoxR.setAlignment(Pos.BOTTOM_LEFT);
        vBoxR.getChildren().addAll(this.totalR, totalRTextField);

        VBox vBoxV = new VBox(5);
        vBoxV.setAlignment(Pos.BOTTOM_LEFT);
        vBoxV.getChildren().addAll(this.totalV, totalVTextField);

        HBox totalHBox = new HBox(20, vBoxG,vBoxR, vBoxV);
        totalHBox.setAlignment(Pos.BOTTOM_LEFT);
        totalHBox.setMaxHeight(30);
        this.mainBox.getChildren().add(totalHBox);
    }

    private void updateTotal(){
        double totalG = 0.0;
        double totalR = 0.0;
        double totalV = 0.0;

        for (Iron iron : ironObservableList) {
            if (iron instanceof Stuobovi){
                totalV += ((Stuobovi) iron).getWeight();
                continue;
            }
            if (iron instanceof Uzengije){
                totalG += ((Uzengije) iron).getWeight();
                continue;
            }
            if (iron instanceof Sipke){
                totalR += ((Sipke) iron).getWeight();
            }
        }

        this.totalGTextField.setText(String.format("%.2f", totalG));
        this.totalRTextField.setText(String.format("%.2f", totalR));
        this.totalVTextField.setText(String.format("%.2f", totalV));
    }

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
            this.updateTotal();
        });

        addButton.setDefaultButton(true);
        dynamicHBox.setAlignment(Pos.CENTER);
        dynamicHBox.getChildren().addAll(a1TextField, meterLabel, amountTextField, kolicinaLabel, addButton);
    }

    private void initAddUzengije(){
        this.ironTypeComboBox.getItems().clear();
        this.ironTypeComboBox.getItems().addAll("G6", "G8", "R8", "R10");
        this.ironTypeComboBox.setValue("G6");

        this.a1TextField.setPromptText("a1 duzina (m)");
        this.a1TextField.setMinWidth(55);
        this.a1TextField.setMaxWidth(55);

        this.a2TextField.setPromptText("a2 duzina (m)");
        this.a2TextField.setMinWidth(55);
        this.a2TextField.setMaxWidth(55);

        this.amountTextField.setPromptText("kolicina stubovi");
        this.amountTextField.setMinWidth(55);
        this.amountTextField.setMaxWidth(55);

        this.addButton.setOnAction(event -> {
            double a1 = Double.parseDouble(a1TextField.getText())/100;
            double a2;
            if (a2TextField.getText().isEmpty() || a2TextField.getText().isBlank()){
                a2 = 0.0;
            }else {
                a2 = Double.parseDouble(a2TextField.getText())/100;
            }

            Uzengije uzengije = new Uzengije(IronType.valueOf(ironTypeComboBox.getValue()), a1, a2, Integer.parseInt(this.amountTextField.getText()), torzionaCheckBox.isSelected());
            ironObservableList.add(uzengije);
            a1TextField.clear();
            a2TextField.clear();
            amountTextField.clear();
            torzionaCheckBox.setSelected(false);
            a1TextField.requestFocus();
            this.updateTotal();
        });

        addButton.setDefaultButton(true);
        dynamicHBox.setAlignment(Pos.CENTER);
        dynamicHBox.getChildren().addAll(a1TextField, a2TextField, amountTextField, torzionaCheckBox, addButton);
    }

    private void initAddStubove(){
        this.ironTypeComboBox.getItems().clear();
        this.ironTypeComboBox.getItems().addAll( "R8", "R10", "R12", "R14", "R16");
        this.ironTypeComboBox.setValue("R10");

        this.uzengijePerMeterTextField.setPromptText("uzengije per meter");
        this.uzengijePerMeterTextField.setMinWidth(45);
        this.uzengijePerMeterTextField.setMaxWidth(45);
        this.uzengijePerMeterTextField.setText("4");

        this.duzinaStubaTextField.setPromptText("duzina stuba (m)");
        this.duzinaStubaTextField.setMinWidth(55);
        this.duzinaStubaTextField.setMaxWidth(55);

        this.a1TextField.setPromptText("a1 duzina (m)");
        this.a1TextField.setMinWidth(55);
        this.a1TextField.setMaxWidth(55);

        this.a2TextField.setPromptText("a2 duzina (m)");
        this.a2TextField.setMinWidth(55);
        this.a2TextField.setMaxWidth(55);

        this.amountTextField.setPromptText("kolicina (kom)");
        this.amountTextField.setMinWidth(55);
        this.amountTextField.setMaxWidth(55);

        this.addButton.setOnAction(e -> {
            double a1 = Double.parseDouble(a1TextField.getText())/100;
            double a2;
            if (a2TextField.getText().isEmpty() || a2TextField.getText().isBlank()){
                a2 = 0.0;
            }else {
                a2 = Double.parseDouble(a2TextField.getText())/100;
            }

            Stuobovi stuobovi = new Stuobovi(IronType.valueOf(ironTypeComboBox.getValue()), Integer.parseInt(amountTextField.getText()), Integer.parseInt(uzengijePerMeterTextField.getText()), Double.parseDouble(duzinaStubaTextField.getText()), a1, a2);
            ironObservableList.add(stuobovi);

            duzinaStubaTextField.clear();
            a1TextField.clear();
            a2TextField.clear();
            amountTextField.clear();
            this.updateTotal();
        });

        addButton.setDefaultButton(true);
        dynamicHBox.setAlignment(Pos.CENTER);
        dynamicHBox.getChildren().addAll(uzengijePerMeterTextField, amountTextField, duzinaStubaTextField, a1TextField, a2TextField, addButton);
    }
}
