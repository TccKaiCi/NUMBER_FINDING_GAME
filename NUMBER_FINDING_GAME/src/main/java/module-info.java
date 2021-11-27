module com.client.number_finding_game {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.client.number_finding_game to javafx.fxml;
    exports com.client.number_finding_game;
    exports com.client.number_finding_game.Controllers;
    opens com.client.number_finding_game.Controllers to javafx.fxml;
    exports com.client.number_finding_game.Models;
    opens com.client.number_finding_game.Models to javafx.fxml;
}