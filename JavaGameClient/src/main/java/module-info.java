module com.mycompany.javagameclient {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.javagameclient to javafx.fxml;
    exports com.mycompany.javagameclient;
}
