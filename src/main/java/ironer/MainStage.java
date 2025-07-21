package ironer;

import ironer.view.MainView;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainStage extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        MainView mainView = new MainView();
        mainView.setTitle("Ironer");
        mainView.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
