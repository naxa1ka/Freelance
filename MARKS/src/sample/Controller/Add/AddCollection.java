package sample.Controller.Add;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import sample.Controller.BaseController;
import sample.Model.Model.CollectionModel;
import sample.Model.Beans.Collection;

import java.net.URL;
import java.util.ResourceBundle;

public class AddCollection extends BaseAddController<Collection> implements Initializable {

    CollectionModel collectionModel = new CollectionModel();

    @FXML
    private TextField name;

    @FXML
    private ComboBox<Collection> comboBox;

    @FXML
    void onAddPressed() {
        if (isEmpty("Заполните название страны", name)) return;

        add(
                collectionModel,
                new Collection(name.getText()),
                "Успешно добавлено!",
                "Такая коллекция уже существует!"
        );
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        collectionModel.initComboBox(comboBox);
    }
}
