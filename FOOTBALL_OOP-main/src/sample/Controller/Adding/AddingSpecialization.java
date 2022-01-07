package sample.Controller.Adding;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import sample.DB.DbHandler;
import sample.Model.Specialization;
import sample.Utils.Alerts;
import sample.Utils.Utils;

public class AddingSpecialization {

    @FXML
    private TextField nameField;

    DbHandler db = DbHandler.getInstance();

    @FXML
    void onPressedAddButton() {
        if (Utils.isEmpty(nameField)){
            Alerts.alertError("Ввод", "Введите все поля!");
            return;
        }
        adding();
    }

    private void adding(){
        if (db.isExistSpecialization(nameField.getText())) {
            Alerts.alertError("Бд", "Специализация уже существует!");
            return;
        }

        db.addSpecialization(new Specialization(nameField.getText()));
        Alerts.alertInfo("Успех", "Успешно добавлено!");
    }

}
