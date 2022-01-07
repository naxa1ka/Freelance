package sample.Controller.Editing;

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

public class EditingFootballer implements Initializable {

    @FXML
    private TextField nameField;

    @FXML
    private TextField allGamesField;

    @FXML
    private TextField successGamesField;

    @FXML
    private ComboBox<Command> commandsComboBox;

    @FXML
    private ComboBox<Specialization> specializationsComboBox;

    private int footballerId;

    DbHandler db = DbHandler.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Initing.initSpecializationsComboBox(specializationsComboBox);
        Initing.initCommandsComboBox(commandsComboBox);
    }

    public void init(Footballer footballer) {
        nameField.setText(footballer.getName());
        allGamesField.setText(String.valueOf(footballer.getAllGames()));
        successGamesField.setText(String.valueOf(footballer.getSuccessGames()));
        specializationsComboBox.setValue(db.getSpecialization(footballer.getSpecialization().getRole()));
        footballerId = footballer.getId();
    }

    @FXML
    void onPressedEditButton() {
        if (Utils.isEmpty(nameField, allGamesField, successGamesField)) {
            Alerts.alertWarning("Ввод", "Заполните все поля");
            return;
        }

        editing();
    }

    private void editing() {
        db.updateFootballer(new Footballer(
                footballerId,
                nameField.getText(),
                specializationsComboBox.getValue(),
                Integer.parseInt(allGamesField.getText()),
                Integer.parseInt(successGamesField.getText()),
                commandsComboBox.getValue()
        ));

        Alerts.alertInfo("Успех", "Изменено!");
    }
}
