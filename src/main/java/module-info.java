module com.example.applicationmodbussql {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires EasyModbusJava;


    opens com.example.applicationmodbussql to javafx.fxml;
    exports com.example.applicationmodbussql;
    exports com.example.applicationmodbussql.Controllers;
    opens com.example.applicationmodbussql.Controllers to javafx.fxml;
    exports com.example.applicationmodbussql.AddClasses;
    opens com.example.applicationmodbussql.AddClasses to javafx.fxml;
}