package sample.Controller.Editing;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import sample.Controller.Initing;
import sample.DB.DbHandler;
import sample.Model.Command;
import sample.Utils.Alerts;
import sample.Utils.Utils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EditingCommand implements Initializable {

    @FXML
    private ComboBox<Command> commandsComboBox;

    @FXML
    private TextField newText;
    DbHandler db = DbHandler.getInstance();

    @FXML
    void onEditButtonPressed() {
        if (Utils.isEmpty(newText)){
            Alerts.alertWarning("Ввод", "Заполните все поля");
            return;
        }

        db.updateCommand(new Command(
                commandsComboBox.getValue().getId(),
                newText.getText())
        );

        commandsComboBox.getItems().clear();
        Initing.initCommandsComboBox(commandsComboBox);
        Alerts.alertInfo("Успех", "Изменено!");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Initing.initCommandsComboBox(commandsComboBox);
    }
}
