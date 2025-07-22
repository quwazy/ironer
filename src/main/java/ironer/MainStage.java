package ironer;

import ironer.view.OrderView;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainStage extends Application {

    @Override
    public void start(Stage stage) throws Exception {
//        MainView mainView = new MainView();
//        mainView.setTitle("Ironer");
//        mainView.show();
        OrderView orderView = new OrderView();
        orderView.setTitle("Ironer");
        orderView.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
