module rgb {
    requires javafx.controls;
    requires javafx.fxml;

    opens rgb to javafx.fxml;
    exports rgb;
}