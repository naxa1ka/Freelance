package sample.Controller.Deleting;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import sample.Controller.Initing;
import sample.DB.DbHandler;
import sample.Model.Command;
import sample.Utils.Alerts;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DeletingCommand implements Initializable {

    @FXML
    private ComboBox<Command> commandsComboBox;

    DbHandler db = DbHandler.getInstance();

    @FXML
    void onDeleteButtonPressed() {
        db.deleteCommand(commandsComboBox.getValue().getId());
        commandsComboBox.getItems().clear();
        Initing.initCommandsComboBox(commandsComboBox);
        Alerts.alertInfo("Успех", "Удалено!");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Initing.initCommandsComboBox(commandsComboBox);
    }
}
