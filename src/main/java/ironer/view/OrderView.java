package ironer.view;

import ironer.controller.SaveController;
import ironer.controller.ShortcutController;
import ironer.controller.WarningController;
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
import javafx.stage.Stage;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
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
    private TextField a1TextField = new TextField();
    private TextField a2TextField = new TextField();
    private TextField duzinaStubaTextField = new TextField();
    private TextField amountTextField = new TextField();
    private TextField sipkePerStub = new TextField();
    private TextField uzengijePerMeterTextField = new TextField();
    private CheckBox torzionaCheckBox = new CheckBox();
    private Button addButton = new Button("Dodaj");
    /// table row
    private TableView<Iron> tableView = new TableView<>();
    /// final row
    private Label totalG = new Label();
    private TextField totalGTextField = new TextField();
    private Label totalR = new Label();
    private TextField totalRTextField = new TextField();
    private Label totalV = new Label();
    private TextField totalVTextField = new TextField();
    private Button saveButton = new Button("Sacuvaj");
    private Button cleanButton = new Button("Ocisti");

    public OrderView(){
        this.mainBox = new VBox(20);
        this.mainBox.setPadding(new Insets(10, 10, 20, 10));
        this.ironObservableList = FXCollections.observableArrayList();
        //init View
        initInfoRow();
        initAddRow();
        initTable();
        initFinal();
        //init Scene & shortcuts
        Scene scene = new Scene(mainBox, 1100, 900);
        ShortcutController.shiftPressed(scene, this.ironShapeComboBox);
//        ShortcutController.savePressed(scene, this);
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
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Function not implemented");
                alert.setHeaderText(null);
                alert.setContentText("This feature is not implemented yet. Please choose another shape.");
                alert.showAndWait();
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
        TableColumn<Iron, Void> drawColumn = getDrawColumn();

        TableColumn<Iron, IronType> fiColumn  = new TableColumn<>("fi");
        fiColumn.setCellValueFactory(new PropertyValueFactory<>("ironType"));
        fiColumn.setStyle("-fx-alignment: CENTER;");
        fiColumn.setMinWidth(50);
        fiColumn.setMaxWidth(50);

        TableColumn<Iron, Double> lengthColumn = new TableColumn<>("Duzina (m) ");
        lengthColumn.setCellValueFactory(new PropertyValueFactory<>("length"));
        lengthColumn.setStyle("-fx-alignment: CENTER;");
        lengthColumn.setMinWidth(300);
        lengthColumn.setMaxWidth(400);

        TableColumn<Iron, Integer> amountColumn = new TableColumn<>("Kolicina (kom) ");
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        amountColumn.setStyle("-fx-alignment: CENTER;");
        amountColumn.setMinWidth(180);
        amountColumn.setMaxWidth(400);

        TableColumn<Iron, Double> weightColumn = new TableColumn<>("Tezina(kg) ");
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        weightColumn.setStyle("-fx-alignment: CENTER;");
        weightColumn.setMinWidth(180);
        weightColumn.setMaxWidth(400);

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setFixedCellSize(70);
        tableView.setMaxWidth(Double.MAX_VALUE);
        tableView.setStyle("-fx-font-size: 18px;");
        tableView.setItems(ironObservableList);
        tableView.getColumns().add(drawColumn);
        tableView.getColumns().add(fiColumn);
        tableView.getColumns().add(lengthColumn);
        tableView.getColumns().add(amountColumn);
        tableView.getColumns().add(weightColumn);
        tableView.setMaxWidth(Double.MAX_VALUE);
        tableView.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(tableView, Priority.ALWAYS);

        this.mainBox.getChildren().addAll(tableView);
    }

    private void initFinal(){
        this.totalG.setText("Uzengije:");
        this.totalGTextField.setText("0.0");
        this.totalR.setText("Sipke:");
        this.totalRTextField.setText("0.0");
        this.totalV.setText("Vezano:");
        this.totalVTextField.setText("0.0");

        this.saveButton.setMinWidth(100);
        this.saveButton.setOnAction(event -> {
            try {
                SaveController saveController = new SaveController();
                saveController.saveOrder(this.identifierTextField.getText(), tableView,
                        Double.parseDouble(this.totalGTextField.getText()), Double.parseDouble(this.totalRTextField.getText()), Double.parseDouble(this.totalVTextField.getText()),
                        this.dateTextField.getText(), this.ordererTextField.getText());
            } catch (NumberFormatException e) {
                new WarningController();
            }
        });

        this.cleanButton.setMinWidth(100);
        this.cleanButton.setOnAction(event -> {
            ironObservableList.clear();
            updateTotal();
        });

        VBox vBoxG = new VBox(5);
        vBoxG.setAlignment(Pos.CENTER_LEFT);
        vBoxG.getChildren().addAll(this.totalG, totalGTextField);

        VBox vBoxR = new VBox(5);
        vBoxR.setAlignment(Pos.CENTER_LEFT);
        vBoxR.getChildren().addAll(this.totalR, totalRTextField);

        VBox vBoxV = new VBox(5);
        vBoxV.setAlignment(Pos.CENTER_LEFT);
        vBoxV.getChildren().addAll(this.totalV, totalVTextField);

        VBox vBoxButtons = new VBox(5);
        vBoxButtons.setAlignment(Pos.CENTER_LEFT);
        vBoxButtons.getChildren().addAll(saveButton, cleanButton);

        HBox totalHBox = new HBox(15, vBoxG,vBoxR, vBoxV, vBoxButtons);
        totalHBox.setAlignment(Pos.CENTER);
        totalHBox.setMaxHeight(30);
        this.mainBox.getChildren().add(totalHBox);
    }

    private void initAddSipke(){
        this.ironTypeComboBox.getItems().clear();
        this.ironTypeComboBox.getItems().addAll("R6", "R8", "R10", "R12", "R14", "R16");
        this.ironTypeComboBox.setValue("R8");

        this.a1TextField.setPromptText("duzina");
        this.a1TextField.setMinWidth(55);
        this.a1TextField.setMaxWidth(60);
        this.a1TextField.requestFocus();
        Label meterLabel = new Label("m");
        HBox hBoxDuzina = new HBox(2);
        hBoxDuzina.setAlignment(Pos.CENTER);
        hBoxDuzina.getChildren().addAll(a1TextField, meterLabel);

        this.amountTextField.setPromptText("komada");
        this.amountTextField.setMinWidth(55);
        this.amountTextField.setMaxWidth(60);
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
                new WarningController();
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
        this.a1TextField.setMaxWidth(55);
        this.a1TextField.requestFocus();
        Label cmLabel = new Label("cm");
        HBox hBoxA1 = new HBox(1);
        hBoxA1.setAlignment(Pos.CENTER);
        hBoxA1.getChildren().addAll(a1TextField, cmLabel);

        this.a2TextField.setPromptText("a2(cm)");
        this.a2TextField.setMinWidth(50);
        this.a2TextField.setMaxWidth(55);
        Label cmmLabel = new Label("cm");
        HBox hBoxA2 = new HBox(1);
        hBoxA2.setAlignment(Pos.CENTER);
        hBoxA2.getChildren().addAll(a2TextField, cmmLabel);

        this.amountTextField.setPromptText("komada");
        this.amountTextField.setMinWidth(55);
        this.amountTextField.setMaxWidth(60);
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
                new WarningController();
            }
        });

        addButton.setDefaultButton(true);
        dynamicHBox.setAlignment(Pos.CENTER);
        dynamicHBox.getChildren().addAll(hBoxA1, hBoxA2, hBoxKolicina, hBoxTorziona, addButton);
    }

    private void initAddStubove(){
        HBox hBoxSipke = new HBox(10);
        hBoxSipke.setAlignment(Pos.CENTER_LEFT);
        HBox hBoxUzengije = new HBox(10);
        hBoxUzengije.setAlignment(Pos.CENTER_LEFT);

        // hBoxSipke
        this.ironTypeComboBox.getItems().clear();
        this.ironTypeComboBox.getItems().addAll( "R8", "R10", "R12", "R14", "R16");
        this.ironTypeComboBox.setValue("R10");

        this.sipkePerStub.setPromptText("sipki po stubu");
        this.sipkePerStub.setText("4");
        this.sipkePerStub.setMinWidth(30);
        this.sipkePerStub.setMaxWidth(30);
        Label sipkePerStubLabel = new Label("sipki/stub");
        HBox hBoxSipkePerStub = new HBox(1);
        hBoxSipkePerStub.setAlignment(Pos.CENTER);
        hBoxSipkePerStub.getChildren().addAll(sipkePerStubLabel, this.sipkePerStub);

        this.amountTextField.setPromptText("komada");
        this.amountTextField.setMinWidth(50);
        this.amountTextField.setMaxWidth(55);
        Label kolicinaLabel = new Label("kom");
        HBox hBoxKolicina = new HBox(1);
        hBoxKolicina.setAlignment(Pos.CENTER);
        hBoxKolicina.getChildren().addAll(amountTextField, kolicinaLabel);

        this.duzinaStubaTextField.setPromptText("duzina");
        this.duzinaStubaTextField.setMinWidth(50);
        this.duzinaStubaTextField.setMaxWidth(55);
        Label duzina = new Label("m");
        HBox hBoxDuzina = new HBox(1);
        hBoxDuzina.setAlignment(Pos.CENTER);
        hBoxDuzina.getChildren().addAll(duzinaStubaTextField, duzina);

        hBoxSipke.getChildren().addAll(this.ironTypeComboBox, hBoxSipkePerStub, hBoxKolicina, hBoxDuzina);

        // hBoxUzengije
        ComboBox<String> uzengijeIronTypeComboBox = new ComboBox<>();
        uzengijeIronTypeComboBox.getItems().addAll("G6", "G8", "R8", "R10");
        uzengijeIronTypeComboBox.setValue("G6");

        this.uzengijePerMeterTextField.setPromptText("uzengije po metru");
        this.uzengijePerMeterTextField.setMinWidth(30);
        this.uzengijePerMeterTextField.setMaxWidth(30);
        this.uzengijePerMeterTextField.setText("4");
        Label uzengijePerMeterLabel = new Label("uzeng/m");
        HBox hBoxUzengijePerMeter = new HBox(7);
        hBoxUzengijePerMeter.setAlignment(Pos.CENTER);
        hBoxUzengijePerMeter.getChildren().addAll(uzengijePerMeterLabel, uzengijePerMeterTextField);

        this.a1TextField.setPromptText("a1(cm)");
        this.a1TextField.setMinWidth(50);
        this.a1TextField.setMaxWidth(55);
        Label cmLabel = new Label("cm");
        HBox hBoxA1 = new HBox(1);
        hBoxA1.setAlignment(Pos.CENTER);
        hBoxA1.getChildren().addAll(a1TextField, cmLabel);

        this.a2TextField.setPromptText("a2(cm)");
        this.a2TextField.setMinWidth(50);
        this.a2TextField.setMaxWidth(55);
        Label cmmLabel = new Label("cm");
        HBox hBoxA2 = new HBox(1);
        hBoxA2.setAlignment(Pos.CENTER);
        hBoxA2.getChildren().addAll(a2TextField, cmmLabel);

        hBoxUzengije.getChildren().addAll(uzengijeIronTypeComboBox, hBoxUzengijePerMeter, hBoxA1, hBoxA2);

        // VBox
        VBox vBoxStubovi = new VBox(9);
        vBoxStubovi.setAlignment(Pos.CENTER);
        vBoxStubovi.getChildren().addAll(hBoxSipke, hBoxUzengije);

        this.addButton.setOnAction(e -> {
            try {
                double a1 = Double.parseDouble(a1TextField.getText())/100;
                double a2;
                if (a2TextField.getText().isEmpty() || a2TextField.getText().isBlank()){
                    a2 = 0.0;
                } else {
                    a2 = Double.parseDouble(a2TextField.getText())/100;
                }

                Stubovi stubovi = new Stubovi(
                        IronType.valueOf(ironTypeComboBox.getValue()),
                        Integer.parseInt(amountTextField.getText()),
                        Integer.parseInt(sipkePerStub.getText()),
                        Double.parseDouble(duzinaStubaTextField.getText()),
                        IronType.valueOf(uzengijeIronTypeComboBox.getValue()),
                        Integer.parseInt(uzengijePerMeterTextField.getText()),
                        a1, a2);

                ironObservableList.add(stubovi);
                duzinaStubaTextField.clear();
                a1TextField.clear();
                a2TextField.clear();
                amountTextField.clear();
                uzengijePerMeterTextField.clear();
                sipkePerStub.clear();
                uzengijePerMeterTextField.setText("4");
                sipkePerStub.setText("4");
                amountTextField.requestFocus();
                this.updateTotal();
            } catch (NumberFormatException e1) {
                new WarningController();
            }
        });

        addButton.setDefaultButton(true);
        dynamicHBox.setAlignment(Pos.CENTER);
        dynamicHBox.getChildren().addAll(vBoxStubovi, addButton);
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

    private TableColumn<Iron, Void> getDrawColumn() {
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
        return drawColumn;
    }
}
