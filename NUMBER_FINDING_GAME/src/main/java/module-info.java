module com.client.number_finding_game {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.json;


    opens com.client.number_finding_game to javafx.fxml;
    exports com.client.number_finding_game.GUI;
    opens com.client.number_finding_game.GUI to javafx.fxml;
    exports com.client.number_finding_game;
    exports com.DTO;
    opens com.DTO to javafx.fxml;
    exports com.BUS;
    opens com.BUS to javafx.fxml;
}