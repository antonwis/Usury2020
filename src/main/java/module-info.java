module fi.metropolia.group8 {
    requires javafx.controls;
    requires javafx.fxml;

    opens fi.metropolia.group8 to javafx.fxml;
    exports fi.metropolia.group8;
}