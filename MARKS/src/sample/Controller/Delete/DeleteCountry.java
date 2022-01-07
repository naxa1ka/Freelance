package sample.Controller.Delete;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import sample.Model.Model.CountryModel;
import sample.Model.Beans.Country;

import java.net.URL;
import java.util.ResourceBundle;

public class DeleteCountry extends BaseDeleteController<Country> implements Initializable {

    CountryModel countryModel = new CountryModel();

    @FXML
    private ComboBox<Country> comboBox;

    @FXML
    void onDelPressed() {
        if (isIncorrectChoose(comboBox)) return;

        delete(countryModel, comboBox);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        countryModel.initComboBox(comboBox);
    }
}
