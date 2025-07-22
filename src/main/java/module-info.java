module ironer.ironer {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
    requires static lombok;
    requires java.desktop;
    exports ironer;
    opens ironer to javafx.fxml;
    opens ironer.model to javafx.base;
}