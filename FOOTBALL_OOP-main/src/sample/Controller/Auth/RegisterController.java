package sample.Controller.Auth;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.Utils.*;
import sample.Utils.ViewLoader;
import sample.DB.DbHandler;
import sample.Model.User;

public class RegisterController {
    @FXML
    private TextField nameField;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button regButton;


    DbHandler db = DbHandler.getInstance();

    @FXML
    void onRegButtonPressed() {
        if (Utils.isEmpty(nameField, loginField, passwordField)){
            Alerts.alertWarning("Ввод", "Введите все поля!");
            return;
        }
        register();
    }

    private void register() {
        if (db.isExistUser(loginField.getText())) {
            Alerts.alertError("Бд", "Пользователь уже существует!");
            return;
        }

        db.addUser(new User(
                loginField.getText(),
                passwordField.getText(),
                nameField.getText()
        ));

        Alerts.alertInfo("Успех", "Вы зарегистрированы!");
    }


    @FXML
    void onEnterLinkPressed() {
        ViewLoader.loadScene(ViewLoader.EScene.ENTER);
        ViewLoader.hideScene(regButton);
    }
}
