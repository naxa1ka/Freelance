package sample.Controller.Add;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import sample.Controller.BaseController;
import sample.Model.Beans.Serie;
import sample.Model.Model.SerieModel;

import java.net.URL;
import java.util.ResourceBundle;

public class AddSerie extends BaseAddController<Serie> implements Initializable {

    SerieModel serieModel = new SerieModel();

    @FXML
    private TextField year;

    @FXML
    private ComboBox<Serie> comboBox;

    @FXML
    private TextField name;

    @FXML
    void onAddPressed() {
        if (isEmpty("Заполните название серии!", name)) return;

        if (isEmpty("Заполните год серии!", year)) return;

        add(
                serieModel,
                new Serie(
                        name.getText(),
                        Integer.parseInt(year.getText())
                ),
                "Успешно добавлено!",
                "Такая страна уже существует!"
        );
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       serieModel.initComboBox(comboBox);
    }
}