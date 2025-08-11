package ironer.controller;

import javafx.scene.control.Alert;

public class WarningController {

    /// for wrong number input
    public WarningController() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Input");
        alert.setHeaderText(null);
        alert.setContentText("Please enter valid numbers in the fields.");
        alert.showAndWait();
    }

    public WarningController(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setTitle("WARNING");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
