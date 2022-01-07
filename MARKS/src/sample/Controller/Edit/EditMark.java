package sample.Controller.Edit;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import sample.Controller.BaseController;
import sample.Model.Model.CountryModel;
import sample.Model.Beans.Country;
import sample.Model.Beans.Mark;
import sample.Model.Beans.Serie;
import sample.Model.Model.MarkModel;
import sample.Model.Model.SerieModel;

import java.net.URL;
import java.util.ResourceBundle;

public class EditMark extends BaseEditController<Mark> implements Initializable {
    SerieModel serieModel = new SerieModel();
    CountryModel countryModel = new CountryModel();
    MarkModel markModel = new MarkModel();

    @FXML
    private ComboBox<Mark> markComboBox;

    @FXML
    private ComboBox<Serie> serieComboBox;

    @FXML
    private ComboBox<Country> countryComboBox;

    @FXML
    void onEditPressed() {
        update(
                markModel,
                markComboBox.getValue(),
                new Mark(countryComboBox.getValue(), serieComboBox.getValue()),
                "Успешно изменено!",
                "Такая марка уже существует!"
        );
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        serieModel.initComboBox(serieComboBox);
        countryModel.initComboBox(countryComboBox);
        markModel.initComboBox(markComboBox);
    }
}
