package sample.Controller.Delete;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import sample.Model.Beans.Serie;
import sample.Model.Model.SerieModel;

import java.net.URL;
import java.util.ResourceBundle;

public class DeleteSerie extends BaseDeleteController<Serie> implements Initializable {

    SerieModel serieModel = new SerieModel();

    @FXML
    private ComboBox<Serie> comboBox;

    @FXML
    void onDelPressed() {
        if (isIncorrectChoose(comboBox)) return;

        delete(serieModel, comboBox);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       serieModel.initComboBox(comboBox);
    }
}