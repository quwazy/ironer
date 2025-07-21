package ironer.view;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MainView extends Stage {
    private Label label;

    public MainView() {
        initView();

    }

    private void initView() {
        label = new Label();
        label.setText("Hello World!");
        Scene scene = new Scene(label, 500,500);
        setScene(scene);
    }
}
