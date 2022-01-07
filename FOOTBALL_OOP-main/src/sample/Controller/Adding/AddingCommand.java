package sample.Controller.Adding;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import sample.DB.DbHandler;
import sample.Model.Command;
import sample.Utils.Alerts;
import sample.Utils.Utils;

public class AddingCommand {

    @FXML
    private TextField nameField;

    DbHandler db = DbHandler.getInstance();

    @FXML
    void onPressedAddButton() {
        if (Utils.isEmpty(nameField)){
            Alerts.alertWarning("Ввод", "Введите все поля");
            return;
        }
        adding();
    }

    private void adding(){
        if (db.isExistCommand(nameField.getText())) {
            Alerts.alertWarning("Добавление", "Команда уже существует!");
            return;
        }

        db.addCommand(new Command(nameField.getText()));
        Alerts.alertInfo("Успех", "Успещно добавление");
    }
}
