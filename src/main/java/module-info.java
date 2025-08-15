module ironer.ironer {
    requires static lombok;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.swing;
    requires com.github.librepdf.openpdf;
    requires javafx.graphics;
    exports ironer;
    opens ironer to javafx.fxml;
    opens ironer.model to javafx.base;
    opens ironer.model.irons to javafx.base, javafx.fxml;
}
