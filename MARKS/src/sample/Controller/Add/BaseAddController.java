package sample.Controller.Add;

import javafx.scene.control.TextField;
import sample.Controller.BaseController;
import sample.Model.Beans.Bean;
import sample.Model.Model.BaseModel;

public class BaseAddController<T extends Bean> extends BaseController{
    public boolean isEmpty(String message, TextField ... textFields){
       if (isEmpty(textFields)){
           alertWarning(message);
           return true;
       }
       return false;
    }
    public void add(BaseModel<T> baseModel, T bean, String successMessage, String errorMessage){
        if (baseModel.add(bean)){
            alertInfo(successMessage);
        } else {
            alertWarning(errorMessage);
        }
    }
}
