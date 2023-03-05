module com.example.c195 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;

    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.example.c195 to javafx.fxml, javafx.base;
    exports com.example.c195;
    exports DBConnection;
    opens DBConnection to javafx.fxml;
    exports Controllers;
    opens Controllers to javafx.fxml;
    opens User to javafx.base;
}