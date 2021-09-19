module mx.umich.fismat.rgb {
    requires javafx.controls;
    requires javafx.fxml;


    opens mx.umich.fismat.rgb to javafx.fxml;
    exports mx.umich.fismat.rgb;
}