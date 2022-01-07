package sample.Controller.Edit;

import javafx.scene.control.TextField;
import sample.Controller.BaseController;
import sample.Model.Beans.Bean;
import sample.Model.Model.BaseModel;

public class BaseEditController<T extends Bean> extends BaseController {
    public boolean isEmpty(String message, TextField... textFields){
        if (isEmpty(textFields)){
            alertWarning(message);
            return true;
        }
        return false;
    }

    public void update(BaseModel<T> baseModel, T oldValue, T newValue, String successMessage, String errorMessage){
        if (baseModel.update(oldValue, newValue)){
            alertInfo(successMessage);
        } else {
            alertWarning(errorMessage);
        }
    }
}
