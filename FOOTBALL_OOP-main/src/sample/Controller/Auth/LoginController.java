package sample.Controller.Auth;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.Controller.MainController;
import sample.Utils.Alerts;
import sample.Utils.Utils;
import sample.Utils.ViewLoader;
import sample.DB.DbHandler;
import sample.Model.User;

public class LoginController {

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button enterButton;

    DbHandler db = DbHandler.getInstance();

    @FXML
    void onEnterButtonPressed() {
        if (Utils.isEmpty(loginField, passwordField)){
            Alerts.alertWarning("Ввод", "Введите все поля!");
            return;
        }
        enter();
    }

    private void enter() {
        if (!db.isExistUser(loginField.getText())) {
            Alerts.alertError("Бд", "Пользователь не существует!");
            return;
        }

        User user = db.getUser(loginField.getText(), passwordField.getText());

        if (user == null){
            Alerts.alertWarning("Вход", "Неверный пароль!");
            return;
        }

        loadMain(user);
    }

    private void loadMain(User user){
        MainController mainController =
                ViewLoader.loadScene(ViewLoader.EScene.MAIN).getController();
        mainController.init(user);
        ViewLoader.hideScene(enterButton);
    }

    @FXML
    void onRegLinkPressed() {
        ViewLoader.loadScene(ViewLoader.EScene.REGISTER);
        ViewLoader.hideScene(enterButton);
    }

}
