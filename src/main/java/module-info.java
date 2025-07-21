module ironer.ironer {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens ironer.ironer to javafx.fxml;
    exports ironer;
    opens ironer to javafx.fxml;
}