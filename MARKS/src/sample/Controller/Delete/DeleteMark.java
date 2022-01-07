package sample.Controller.Delete;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import sample.Model.Beans.Mark;
import sample.Model.Model.MarkModel;

import java.net.URL;
import java.util.ResourceBundle;

public class DeleteMark extends BaseDeleteController<Mark> implements Initializable {
    MarkModel markModel = new MarkModel();

    @FXML
    private ComboBox<Mark> markComboBox;

    @FXML
    void onDelPressed() {
        if (isIncorrectChoose(markComboBox)) return;

        delete(markModel, markComboBox);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       markModel.initComboBox(markComboBox);
    }
}
