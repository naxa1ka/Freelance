package sample.Controller.Adding;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import sample.Controller.Initing;
import sample.DB.DbHandler;
import sample.Model.Command;
import sample.Model.Footballer;
import sample.Model.Specialization;
import sample.Utils.Alerts;
import sample.Utils.Utils;
import java.net.URL;
import java.util.ResourceBundle;

public class AddingFootballer implements Initializable {

    @FXML
    private TextField nameField;

    @FXML
    private TextField allGamesField;

    @FXML
    private TextField successGamesField;

    @FXML
    private ComboBox<Specialization> specializationsComboBox;

    @FXML
    private ComboBox<Command> commandsComboBox;

    DbHandler db = DbHandler.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Initing.initSpecializationsComboBox(specializationsComboBox);
        Initing.initCommandsComboBox(commandsComboBox);
    }

    @FXML
    void onPressedAddButton() {
        if (Utils.isEmpty(nameField, allGamesField, successGamesField)) {
            Alerts.alertError("Ввод", "Введите все поля!");
            return;
        }
        adding();
    }
    private void adding(){
        if (db.isExistFootballer(nameField.getText())) {
            Alerts.alertWarning("Добавление", "Футболист уже существует!");
            return;
        }

        db.addFootballer(new Footballer(
                nameField.getText(),
                specializationsComboBox.getValue(),
                Integer.parseInt(allGamesField.getText()),
                Integer.parseInt(successGamesField.getText()),
                commandsComboBox.getValue()
        ));
        Alerts.alertInfo("Успех", "Успешно добавлено!");
    }
}
