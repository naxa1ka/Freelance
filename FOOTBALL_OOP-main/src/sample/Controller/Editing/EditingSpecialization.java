package sample.Controller.Editing;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import sample.Controller.Initing;
import sample.DB.DbHandler;
import sample.Model.Specialization;
import sample.Utils.Alerts;
import sample.Utils.Utils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EditingSpecialization implements Initializable {

    @FXML
    private ComboBox<Specialization> specializationsComboBox;

    @FXML
    private TextField newText;

    DbHandler db = DbHandler.getInstance();

    @FXML
    void onEditButtonPressed() {
        if (Utils.isEmpty(newText)){
            Alerts.alertWarning("Ввод", "Заполните все поля");
            return;
        }

        db.updateSpecialization(new Specialization(
                specializationsComboBox.getValue().getId(),
                newText.getText()
        ));

        specializationsComboBox.getItems().clear();
        Initing.initSpecializationsComboBox(specializationsComboBox);
        Alerts.alertInfo("Успех", "Изменено!");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Initing.initSpecializationsComboBox(specializationsComboBox);
    }

}
