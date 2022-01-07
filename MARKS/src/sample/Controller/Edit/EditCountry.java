package sample.Controller.Edit;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import sample.Controller.BaseController;
import sample.Model.Model.CountryModel;
import sample.Model.Beans.Country;

import java.net.URL;
import java.util.ResourceBundle;

public class EditCountry extends BaseEditController<Country> implements Initializable {

    CountryModel countryModel = new CountryModel();

    @FXML
    private TextField name;

    @FXML
    private ComboBox<Country> comboBox;

    @FXML
    void onEditPressed() {
        if (isEmpty("Заполните название страны!", name)) return;

        update(
                countryModel,
                comboBox.getValue(),
                new Country(name.getText()),
                "Успешно изменено!",
                "Такая страна уже существует!"
        );
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       countryModel.initComboBox(comboBox);
    }
}
