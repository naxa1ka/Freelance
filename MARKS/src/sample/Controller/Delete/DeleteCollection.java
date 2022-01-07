package sample.Controller.Delete;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import sample.Model.Model.CollectionModel;
import sample.Model.Beans.Collection;

import java.net.URL;
import java.util.ResourceBundle;

public class DeleteCollection extends BaseDeleteController<Collection> implements Initializable {

    CollectionModel collectionModel = new CollectionModel();

    @FXML
    private ComboBox<Collection> comboBox;

    @FXML
    void onDelPressed() {
        if (isIncorrectChoose(comboBox)) return;

        delete(collectionModel, comboBox);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        collectionModel.initComboBox(comboBox);
    }
}
