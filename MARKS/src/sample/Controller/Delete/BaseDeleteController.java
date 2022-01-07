package sample.Controller.Delete;

import javafx.scene.control.ComboBox;
import sample.Controller.BaseController;
import sample.Model.Beans.Bean;
import sample.Model.Model.BaseModel;

public class BaseDeleteController<T extends Bean> extends BaseController {

    public boolean isIncorrectChoose(ComboBox<T> comboBox){
        if (comboBox.getValue() == null){
            alertWarning("Ничего не выбрано!");
            return true;
        }
        return false;
    }

    public void delete(BaseModel<T> baseModel, ComboBox<T> comboBox){
        baseModel.delete(comboBox.getValue());
        alertInfo("Успешно удалено!");
    }

}
