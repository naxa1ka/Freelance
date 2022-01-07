package sample.Controller.Deleting;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import sample.Controller.Initing;
import sample.DB.DbHandler;
import sample.Model.Specialization;
import sample.Utils.Alerts;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DeletingSpecialization implements Initializable {

    @FXML
    private ComboBox<Specialization> specializationsComboBox;

    DbHandler db = DbHandler.getInstance();

    @FXML
    void onDeleteButtonPressed() {
        db.deleteSpecialization(specializationsComboBox.getValue().getId());
        specializationsComboBox.getItems().clear();
        Initing.initSpecializationsComboBox(specializationsComboBox);
        Alerts.alertInfo("Успех", "Удалено!");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Initing.initSpecializationsComboBox(specializationsComboBox);
    }

}
