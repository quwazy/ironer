module ironer.ironer {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires static lombok;

    opens ironer.ironer to javafx.fxml;
    exports ironer;
    opens ironer to javafx.fxml;
}