package sample.Controller.Edit;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import sample.Controller.BaseController;
import sample.Model.Beans.Serie;
import sample.Model.Model.SerieModel;

import java.net.URL;
import java.util.ResourceBundle;

public class EditSerie extends BaseEditController<Serie> implements Initializable {

    SerieModel serieModel = new SerieModel();

    @FXML
    private TextField year;

    @FXML
    private ComboBox<Serie> comboBox;

    @FXML
    private TextField name;

    @FXML
    void onEditPressed() {
        if (isEmpty("Заполните название серии!", name)) return;

        try {
            Integer.parseInt(year.getText());
        } catch (NumberFormatException e) {
            alertInfo("Неправильный формат числа!");
            return;
        }

        update(
                serieModel,
                comboBox.getValue(),
                new Serie(name.getText(), Integer.parseInt(year.getText())),
                        "Успешно изменено!",
                        "Такая серия уже существует!"
                );
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        serieModel.initComboBox(comboBox);
    }
}