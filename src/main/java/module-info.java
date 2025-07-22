module ironer.ironer {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires static lombok;
    exports ironer;
    opens ironer to javafx.fxml;
    opens ironer.model to javafx.base;
    opens ironer.model.irons to javafx.base, javafx.fxml;
}