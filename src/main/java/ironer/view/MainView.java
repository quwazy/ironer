package ironer.view;

import ironer.model.Iron;
import ironer.model.enums.IronShape;
import ironer.model.enums.IronType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainView extends Stage {
    private VBox mainBox;
    private List<Iron> ironList;
    private ObservableList<Iron> ironObservableList;
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
    /// preview row
//    private ListView<Iron> listView = new ListView<>();
    private TableView tableView = new TableView<>();
    /// final row
    private Label totalG = new Label();
    private TextField totalGTextField = new TextField();
    private Label totalR = new Label();
    private TextField totalRTextField = new TextField();
    private Label totalV = new Label();
    private TextField totalVTextField = new TextField();

    public MainView() {
        this.mainBox = new VBox(20);
        this.ironList = new ArrayList<>();
        this.ironObservableList = FXCollections.observableArrayList();

        initInfoRow();
        initAddRow();
        initPreview();
        initTable();
        initFinal();

        Scene scene = new Scene(mainBox, 850, 700);
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
            ironObservableList.add(iron);
//            listView.getItems().add(iron);
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
            ironObservableList.add(iron);
//            listView.getItems().add(iron);
            System.out.println(iron.toString());
        });
        addButton.setDefaultButton(true);

        this.dynamicHBox.getChildren().addAll(torzionaCheckBox, a1TextField, a2TextField, amountTextField, addButton);
    }

    private void initPreview(){
//        this.mainBox.getChildren().add(listView);
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

    private void initTable(){
        TableColumn drawColumn  = new TableColumn("Skica");
        drawColumn.setMaxWidth(400);
        drawColumn.setCellFactory(col -> new TableCell<Void, Void>() {
            private final Line line = new Line(0, 0, 50, 0); // x1, y1, x2, y2

            {
                line.setStrokeWidth(2); // Optional: set line thickness
                line.setStroke(Color.BLACK); // Optional: line color
            }

            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    StackPane pane = new StackPane(line);
                    pane.setAlignment(Pos.CENTER); // center in cell
                    setGraphic(pane);
                }
            }
        });
        TableColumn fiColumn  = new TableColumn("fi");
        fiColumn.setCellValueFactory(new PropertyValueFactory<Iron, IronType>("ironType"));
        TableColumn lengthColumn = new TableColumn("Duzina");
        lengthColumn.setMaxWidth(Double.MAX_VALUE);
        lengthColumn.setMinWidth(300);
        lengthColumn.setCellValueFactory(new PropertyValueFactory<Iron, Double>("length"));
        TableColumn amountColumn = new TableColumn("Kolicina");
        amountColumn.setCellValueFactory(new PropertyValueFactory<Iron, Integer>("amount"));
        TableColumn weightColumn = new TableColumn("Tezina(kg)");
        weightColumn.setCellValueFactory(new PropertyValueFactory<Iron, Double>("weight"));

        tableView.setFixedCellSize(80);
//        tableView.getStylesheets().add(Objects.requireNonNull(getClass().getResource("src/main/resources/style.css")).toExternalForm());
        tableView.setMaxWidth(Double.MAX_VALUE);
        tableView.setStyle("-fx-font-size: 18px;");
        this.tableView.setItems(ironObservableList);
        this.tableView.getColumns().addAll(drawColumn, fiColumn, lengthColumn, amountColumn, weightColumn);

        this.mainBox.getChildren().addAll(this.tableView);
    }

}
