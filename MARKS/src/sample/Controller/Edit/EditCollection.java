package sample.Controller.Edit;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import sample.Controller.BaseController;
import sample.Model.Model.CollectionModel;
import sample.Model.Beans.Collection;

import java.net.URL;
import java.util.ResourceBundle;

public class EditCollection extends BaseEditController<Collection> implements Initializable {

    CollectionModel collectionModel = new CollectionModel();

    @FXML
    private TextField name;

    @FXML
    private ComboBox<Collection> comboBox;

    @FXML
    void onEditPressed() {
        if (isEmpty("Заполните название страны!", name)) return;

        update(
                collectionModel,
                comboBox.getValue(),
                new Collection(name.getText()),
                "Успешно изменено!",
                "Такая коллекция уже существует!"
                );
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        collectionModel.initComboBox(comboBox);
    }
}
