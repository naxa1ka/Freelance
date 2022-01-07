package sample.Controller.Add;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import sample.Controller.BaseController;
import sample.Model.Beans.Country;
import sample.Model.Model.CountryModel;

import java.net.URL;
import java.util.ResourceBundle;

public class AddCountry extends BaseAddController<Country> implements Initializable {

    CountryModel countryModel = new CountryModel();

    @FXML
    private TextField name;

    @FXML
    private ComboBox<Country> comboBox;

    @FXML
    void onAddPressed() {
        if (isEmpty("Заполните название страны!", name)) return;

        add(
                countryModel,
                new Country(name.getText()),
                "Успешно добавлено!",
                "Такая страна уже существует!"
        );
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        countryModel.initComboBox(comboBox);
    }
}
