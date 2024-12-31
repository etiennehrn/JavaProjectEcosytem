module com.example.map {
    requires javafx.controls;
    requires javafx.fxml;
    requires core;


    opens com.example.map to javafx.fxml;
    exports com.example.map;
}