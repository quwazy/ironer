package ironer.controller;

import ironer.view.OrderView;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ShortcutController {

    public static void shiftPressed(Scene scene, ComboBox<String> comboBox) {
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.SHIFT) {
                if (!comboBox.isShowing() && !comboBox.getItems().isEmpty()) {
                    int size = comboBox.getItems().size();
                    int current = Math.max(comboBox.getSelectionModel().getSelectedIndex(), -1);
                    int next = (current + 1) % size;
                    comboBox.getSelectionModel().select(next);
                    event.consume();
                }
            }
        });
    }

    public static void savePressed(Scene scene, OrderView orderView){
        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.isControlDown() && event.getCode() == KeyCode.S) {
                try {
                    SaveController saveController = new SaveController();
                    saveController.saveOrder(
                            orderView.getIdentifierTextField().getText(),
                            orderView.getTableView(),
                            Double.parseDouble(orderView.getTotalGTextField().getText()),
                            Double.parseDouble(orderView.getTotalRTextField().getText()),
                            Double.parseDouble(orderView.getTotalVTextField().getText()),
                            Double.parseDouble(orderView.getTotalMTextField().getText()),
                            orderView.getDateTextField().getText(),
                            orderView.getOrdererTextField().getText(),
                            orderView.getNoteTextField().getText()
                    );
                } catch (NumberFormatException e) {
                    new WarningController();
                }
            }
        });
    }
}
