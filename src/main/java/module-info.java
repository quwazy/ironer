module ironer.ironer {
    requires static lombok;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires itextpdf;
    requires javafx.swing;
    exports ironer;
    opens ironer to javafx.fxml;
    opens ironer.model to javafx.base;
    opens ironer.model.irons to javafx.base, javafx.fxml;
}
