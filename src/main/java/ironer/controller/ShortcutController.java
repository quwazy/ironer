package ironer.controller;

import ironer.view.OrderView;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;

public class ShortcutController {

    public static void shiftPressed(Scene scene, ComboBox<String> comboBox){
        scene.setOnKeyPressed(event -> {
            if (event.isShiftDown()) {
                int currentIndex = comboBox.getSelectionModel().getSelectedIndex();
                int nextIndex = (currentIndex + 1) % comboBox.getItems().size();
                comboBox.getSelectionModel().select(nextIndex);
            }
        });
    }

    public static void savePressed(Scene scene, OrderView orderView){
        scene.setOnKeyPressed(event -> {
            if (event.isControlDown() && event.getCode().toString().equals("S")) {
                try {
                    SaveController saveController = new SaveController();
                    saveController.saveOrder(
                            orderView.getIdentifierTextField().getText(),
                            orderView.getTableView(),
                            Double.parseDouble(orderView.getTotalGTextField().getText()),
                            Double.parseDouble(orderView.getTotalRTextField().getText()),
                            Double.parseDouble(orderView.getTotalVTextField().getText()),
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
