package ironer.view;

import ironer.controller.ClearListAction;
import ironer.controller.PrintController;
import ironer.model.enums.IronShape;
import ironer.model.enums.IronType;
import ironer.model.irons.Iron;
import ironer.model.irons.Sipke;
import ironer.model.irons.Stubovi;
import ironer.model.irons.Uzengije;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.time.LocalDateTime;

public class OrderView extends Stage {
    /// order view
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
    private TextField sipkePerStub = new TextField();
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
    private Button printButton = new Button("Printaj");

    public OrderView(){
        this.mainBox = new VBox(20);
        this.mainBox.setPadding(new Insets(10, 10, 20, 10));
        this.ironObservableList = FXCollections.observableArrayList();
        //init view
        initInfoRow();
        initAddRow();
        initTable();
        initFinal();
        //init Scene
        Scene scene = new Scene(mainBox, 1100, 800);
        this.setScene(scene);
    }

    private void initInfoRow(){
        this.identifierLabel.setText("Oznaka:");
        this.identifierTextField.setPromptText("oznaka");
        this.identifierTextField.setText("RN");

        this.ordererLabel.setText("Narucilac:");
        this.ordererTextField.setPromptText("narucilac");
        this.ordererTextField.setText(" ");

        this.dateLabel.setText("Datum:");
        this.dateTextField.setPromptText("datum");
        this.dateTextField.setText(LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy.")));

        HBox hBox = new HBox(7);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(identifierLabel, identifierTextField, ordererLabel, ordererTextField, dateLabel, dateTextField);
        this.mainBox.getChildren().addAll(hBox);
    }

    private void initAddRow(){
        //Shape Combo Box
        for (IronShape ironShape : IronShape.values()) {
            this.ironShapeComboBox.getItems().add(ironShape.name());
        }
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
            else if (IronShape.GVOZDJE_N.name().equals(this.ironShapeComboBox.getValue())) {
                //TODO
            }
        });
        this.ironShapeComboBox.setValue(IronShape.SIPKE.name());
        initAddSipke();

        HBox hBox = new HBox(14);
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
                    Iron iron = getTableView().getItems().get(getIndex());
                    if (iron != null) {
                        Group drawing = iron.getDraw();
                        StackPane pane = new StackPane(drawing);
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
        fiColumn.setMinWidth(45);
        fiColumn.setMaxWidth(45);

        TableColumn<Iron, Double> lengthColumn = new TableColumn<>("Duzina (m) ");
        lengthColumn.setCellValueFactory(new PropertyValueFactory<>("length"));
        lengthColumn.setMinWidth(200);
        lengthColumn.setMaxWidth(400);

        TableColumn<Iron, Integer> amountColumn = new TableColumn<>("Kolicina (kom) ");
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        lengthColumn.setMinWidth(200);
        lengthColumn.setMaxWidth(400);

        TableColumn<Iron, Double> weightColumn = new TableColumn<>("Tezina(kg) ");
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        lengthColumn.setMinWidth(200);
        lengthColumn.setMaxWidth(400);
        weightColumn.setStyle("-fx-alignment: CENTER;");

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableView.setFixedCellSize(70);
//        tableView.getStylesheets().add(Objects.requireNonNull(getClass().getResource("src/main/resources/style.css")).toExternalForm());
        tableView.setMaxWidth(Double.MAX_VALUE);
        tableView.setStyle("-fx-font-size: 18px;");
        this.tableView.setItems(ironObservableList);
        this.tableView.getColumns().addAll(drawColumn, fiColumn, lengthColumn, amountColumn, weightColumn);
        VBox.setVgrow(tableView, Priority.ALWAYS);
        tableView.setMaxWidth(Double.MAX_VALUE);
        tableView.setMaxHeight(Double.MAX_VALUE);

        this.mainBox.getChildren().addAll(this.tableView);
    }

    private void initFinal(){
        this.totalG.setText("Uzengije");
        this.totalGTextField.setText("0.0");
        this.totalR.setText("Sipke");
        this.totalRTextField.setText("0.0");
        this.totalV.setText("Vezano");
        this.totalVTextField.setText("0.0");

        this.printButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save PDF File");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            File file = fileChooser.showSaveDialog(this);

            if (file != null) {
                PrintController printController = new PrintController();
                printController.exportTableViewToPdf(tableView, file.getAbsolutePath(), Double.parseDouble(this.totalGTextField.getText()), Double.parseDouble(this.totalRTextField.getText()), Double.parseDouble(this.totalVTextField.getText()), this.identifierTextField.getText(), this.dateTextField.getText(), this.ordererTextField.getText());
            }

        });

        this.removeButton.setOnAction(event -> {
            ClearListAction clearListAction = new ClearListAction();
            clearListAction.clearList(ironObservableList);
            updateTotal();
        });

        VBox vBoxG = new VBox(5);
        vBoxG.setAlignment(Pos.CENTER_LEFT);
        vBoxG.getChildren().addAll(this.totalG, totalGTextField);

        VBox vBoxR = new VBox(5);
        vBoxR.setAlignment(Pos.BOTTOM_LEFT);
        vBoxR.getChildren().addAll(this.totalR, totalRTextField);

        VBox vBoxV = new VBox(5);
        vBoxV.setAlignment(Pos.BOTTOM_LEFT);
        vBoxV.getChildren().addAll(this.totalV, totalVTextField);

        HBox totalHBox = new HBox(10, vBoxG,vBoxR, vBoxV, printButton, removeButton);
        totalHBox.setAlignment(Pos.CENTER_LEFT);
        totalHBox.setMaxHeight(30);
        this.mainBox.getChildren().add(totalHBox);
    }

    private void updateTotal(){
        double totalG = 0.0;
        double totalR = 0.0;
        double totalV = 0.0;

        for (Iron iron : ironObservableList) {
            if (iron instanceof Stubovi){
                totalV += ((Stubovi) iron).getWeight();
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
        this.ironTypeComboBox.setValue("R8");

        this.a1TextField.setPromptText("duzina (m)");
        this.a1TextField.setMinWidth(55);
        this.a1TextField.setMaxWidth(55);
        this.a1TextField.requestFocus();
        Label meterLabel = new Label("metara");
        HBox hBoxDuzina = new HBox(2);
        hBoxDuzina.setAlignment(Pos.CENTER);
        hBoxDuzina.getChildren().addAll(a1TextField, meterLabel);

        this.amountTextField.setPromptText("komada");
        this.amountTextField.setMinWidth(55);
        this.amountTextField.setMaxWidth(55);
        Label kolicinaLabel = new Label("kom");
        HBox hBoxKolicina = new HBox(2);
        hBoxKolicina.setAlignment(Pos.CENTER);
        hBoxKolicina.getChildren().addAll(amountTextField, kolicinaLabel);

        this.addButton.setOnAction(event -> {
            try {
                Sipke sipke = new Sipke(IronType.valueOf(ironTypeComboBox.getValue()), Double.parseDouble(a1TextField.getText()), Integer.parseInt(amountTextField.getText()));
                ironObservableList.add(sipke);
                a1TextField.clear();
                amountTextField.clear();
                a1TextField.requestFocus();
                this.updateTotal();
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Input");
                alert.setHeaderText(null);
                alert.setContentText("Please enter valid numbers in the fields.");
                alert.showAndWait();
            }
        });

        addButton.setDefaultButton(true);
        dynamicHBox.setAlignment(Pos.CENTER);
        dynamicHBox.getChildren().addAll(hBoxDuzina, hBoxKolicina, addButton);
    }

    private void initAddUzengije(){
        this.ironTypeComboBox.getItems().clear();
        this.ironTypeComboBox.getItems().addAll("G6", "G8", "R8", "R10");
        this.ironTypeComboBox.setValue("G6");

        this.a1TextField.setPromptText("a1(cm)");
        this.a1TextField.setMinWidth(50);
        this.a1TextField.setMaxWidth(50);
        this.a1TextField.requestFocus();
        Label cmLabel = new Label("cm");
        HBox hBoxA1 = new HBox(1);
        hBoxA1.setAlignment(Pos.CENTER);
        hBoxA1.getChildren().addAll(a1TextField, cmLabel);

        this.a2TextField.setPromptText("a2(cm)");
        this.a2TextField.setMinWidth(50);
        this.a2TextField.setMaxWidth(50);
        Label cmmLabel = new Label("cm");
        HBox hBoxA2 = new HBox(1);
        hBoxA2.setAlignment(Pos.CENTER);
        hBoxA2.getChildren().addAll(a2TextField, cmmLabel);

        this.amountTextField.setPromptText("komada");
        this.amountTextField.setMinWidth(55);
        this.amountTextField.setMaxWidth(55);
        Label kolicinaLabel = new Label("kom");
        HBox hBoxKolicina = new HBox(2);
        hBoxKolicina.setAlignment(Pos.CENTER);
        hBoxKolicina.getChildren().addAll(amountTextField, kolicinaLabel);

        Label torzionaLabel = new Label("torz");
        HBox hBoxTorziona = new HBox(1);
        hBoxTorziona.setAlignment(Pos.CENTER);
        hBoxTorziona.getChildren().addAll(torzionaCheckBox, torzionaLabel);

        this.addButton.setOnAction(event -> {
            try {
                double a1 = Double.parseDouble(a1TextField.getText())/100;
                double a2;
                if (a2TextField.getText().isEmpty() || a2TextField.getText().isBlank()){
                    a2 = 0.0;
                } else {
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
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Input");
                alert.setHeaderText(null);
                alert.setContentText("Please enter valid numbers in the fields.");
                alert.showAndWait();
            }
        });

        addButton.setDefaultButton(true);
        dynamicHBox.setAlignment(Pos.CENTER);
        dynamicHBox.getChildren().addAll(hBoxA1, hBoxA2, hBoxKolicina, hBoxTorziona, addButton);
    }

    private void initAddStubove(){
        this.ironTypeComboBox.getItems().clear();
        this.ironTypeComboBox.getItems().addAll( "R8", "R10", "R12", "R14", "R16");
        this.ironTypeComboBox.setValue("R10");

        this.duzinaStubaTextField.setPromptText("(metara)");
        this.duzinaStubaTextField.setMinWidth(50);
        this.duzinaStubaTextField.setMaxWidth(50);
        Label duzina = new Label("duzina:");
        HBox hBoxDuzina = new HBox(1);
        hBoxDuzina.setAlignment(Pos.CENTER);
        hBoxDuzina.getChildren().addAll(duzina, duzinaStubaTextField);

        this.amountTextField.setPromptText("komada");
        this.amountTextField.setMinWidth(55);
        this.amountTextField.setMaxWidth(55);
        Label kolicinaLabel = new Label("kom:");
        HBox hBoxKolicina = new HBox(1);
        hBoxKolicina.setAlignment(Pos.CENTER);
        hBoxKolicina.getChildren().addAll(kolicinaLabel, amountTextField);

        this.sipkePerStub.setPromptText("sipke po stubu");
        this.sipkePerStub.setText("4");
        this.sipkePerStub.setMinWidth(30);
        this.sipkePerStub.setMaxWidth(30);
        Label sipkePerStubLabel = new Label("sipki");
        HBox hBoxSipkePerStub = new HBox(1);
        hBoxSipkePerStub.setAlignment(Pos.CENTER);
        hBoxSipkePerStub.getChildren().addAll(sipkePerStubLabel, this.sipkePerStub);

        //Uzengije od stuba
        ComboBox<String> uzengijeIronTypeComboBox = new ComboBox<>();
        uzengijeIronTypeComboBox.getItems().addAll("G6", "G8", "R8", "R10");
        uzengijeIronTypeComboBox.setValue("G6");

        this.uzengijePerMeterTextField.setPromptText("uzengije po metru");
        this.uzengijePerMeterTextField.setMinWidth(30);
        this.uzengijePerMeterTextField.setMaxWidth(30);
        this.uzengijePerMeterTextField.setText("4");
        Label uzengijePerMeterLabel = new Label("uz/m:");
        HBox hBoxUzengijePerMeter = new HBox(1);
        hBoxUzengijePerMeter.setAlignment(Pos.CENTER);
        hBoxUzengijePerMeter.getChildren().addAll(uzengijePerMeterLabel, uzengijePerMeterTextField);

        this.a1TextField.setPromptText("a1(cm)");
        this.a1TextField.setMinWidth(50);
        this.a1TextField.setMaxWidth(50);
        Label cmLabel = new Label("cm");
        HBox hBoxA1 = new HBox(1);
        hBoxA1.setAlignment(Pos.CENTER);
        hBoxA1.getChildren().addAll(a1TextField, cmLabel);

        this.a2TextField.setPromptText("a2(cm)");
        this.a2TextField.setMinWidth(50);
        this.a2TextField.setMaxWidth(50);
        Label cmmLabel = new Label("cm");
        HBox hBoxA2 = new HBox(1);
        hBoxA2.setAlignment(Pos.CENTER);
        hBoxA2.getChildren().addAll(a2TextField, cmmLabel);

        HBox hBoxUzengije = new HBox(3);
        hBoxUzengije.setAlignment(Pos.CENTER);
        hBoxUzengije.getChildren().addAll(uzengijeIronTypeComboBox, hBoxUzengijePerMeter, hBoxA1, hBoxA2);

        this.addButton.setOnAction(e -> {
            try {
                double a1 = Double.parseDouble(a1TextField.getText())/100;
                double a2;
                if (a2TextField.getText().isEmpty() || a2TextField.getText().isBlank()){
                    a2 = 0.0;
                } else {
                    a2 = Double.parseDouble(a2TextField.getText())/100;
                }

                Stubovi stubovi = new Stubovi(IronType.valueOf(ironTypeComboBox.getValue()), Integer.parseInt(amountTextField.getText()), Integer.parseInt(this.sipkePerStub.getText()), Double.parseDouble(duzinaStubaTextField.getText()), IronType.valueOf(uzengijeIronTypeComboBox.getValue()), Integer.parseInt(uzengijePerMeterTextField.getText()), a1, a2);
                ironObservableList.add(stubovi);
                duzinaStubaTextField.clear();
                a1TextField.clear();
                a2TextField.clear();
                amountTextField.clear();
                amountTextField.requestFocus();
                this.updateTotal();
            } catch (NumberFormatException e1) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Input");
                alert.setHeaderText(null);
                alert.setContentText("Please enter valid numbers in the fields.");
                alert.showAndWait();
            }
        });

        addButton.setDefaultButton(true);
        dynamicHBox.setAlignment(Pos.CENTER);
        dynamicHBox.getChildren().addAll(hBoxKolicina, hBoxSipkePerStub, hBoxDuzina, hBoxUzengije, addButton);
    }
}
