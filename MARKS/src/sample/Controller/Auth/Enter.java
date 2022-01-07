package sample.Controller.Auth;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import sample.Controller.BaseController;
import sample.Model.Beans.User;
import sample.Model.Model.UserModel;
import sample.Loader.SceneView;


public class Enter extends BaseController {
    @FXML
    private TextField login;

    @FXML
    private TextField password;

    UserModel userModel = new UserModel();

    @FXML
    void onEnterButtonPressed() {
        if (isEmpty(login)) {
            alertWarning("Введите логин!");
            return;
        }

        if (isEmpty(password)) {
            alertWarning("Введите пароль!");
            return;
        }

        User user = userModel.login(login.getText(), password.getText());
        if (user == null) {
            alertWarning("Ошибка входа!");
            return;
        }

    }

    @FXML
    void onRegisterLinkPressed() {
        loadScene(SceneView.ADD_MARK);
        hideScene(login);
    }
}
