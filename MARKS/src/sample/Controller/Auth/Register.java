package sample.Controller.Auth;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import sample.Controller.BaseController;
import sample.Model.Beans.User;
import sample.Model.Model.UserModel;
import sample.Loader.SceneView;

public class Register extends BaseController {
    @FXML
    private TextField login;

    @FXML
    private TextField password;

    UserModel userModel = new UserModel();

    @FXML
    void onRegButtonPressed() {
        if (isEmpty(login)){
            alertWarning("Заполните логин!");
            return;
        }

        if (isEmpty(password)){
            alertWarning("Заполните пароль!");
            return;
        }

        if (userModel.register(new User(login.getText(), password.getText()))){
            alertInfo("Успешная регистрация!");
        } else {
            alertWarning("Пользователь существует!");
        }
    }

    @FXML
    void onEnterLinkPressed() {
        loadScene(SceneView.ENTER);
        hideScene(login);
    }
}
