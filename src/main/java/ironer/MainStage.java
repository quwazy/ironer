package ironer;

import ironer.view.OrderView;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainStage extends Application {

    @Override
    public void start(Stage stage) {
        OrderView orderView = new OrderView();
        orderView.setTitle("Kreator radnih naloga");
        orderView.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
