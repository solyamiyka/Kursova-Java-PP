module com.example.kurs {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.sql;
    requires mysql.connector.java;


    opens com.example.kurs to javafx.fxml;
    opens com.example.kurs.taxes to javafx.fxml;
    exports com.example.kurs;
    exports com.example.kurs.taxes;
}