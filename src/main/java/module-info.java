module com.grupo4.proyecto {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.base;

    opens com.grupo4.proyecto to javafx.fxml;
    exports com.grupo4.proyecto;
}
