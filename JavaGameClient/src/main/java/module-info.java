module com.mycompany.javagameclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.base;

    opens com.mycompany.javagameclient to javafx.fxml;
    exports com.mycompany.javagameclient;
}
