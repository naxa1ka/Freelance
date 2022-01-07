package sample.Utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Controller.MainController;
import sample.Main;
import sample.Model.User;

import java.io.IOException;

public class ViewLoader {
    private static final String way = "View/";
    private static final String auth = "Auth/";
    private static final String adding = "Adding/";
    private static final String deleting = "Deleting/";
    private static final String editing = "Editing/";

    public enum EScene {
        ENTER(way + auth + "login.fxml", "Авторизация"),
        REGISTER(way + auth + "register.fxml", "Регистрация"),

        CALENDAR(way + "calendar.fxml", "Календарь игр"),

        ADDING_MATCH(way + adding + "addingMatch.fxml", "Добавление матча"),
        ADDING_FOOTBALLER(way + adding + "addingFootballer.fxml", "Добавление футболиста"),
        ADDING_SPECIALIZATION(way + adding + "addingSpecialization.fxml", "Добавление специализации"),
        ADDING_COMMAND(way + adding + "addingCommand.fxml", "Добавление команды"),

        EDITING_FOOTBALLER(way + editing + "editingFootballer.fxml", "Редактирование футболиста"),
        EDITING_SPECIALIZATION(way + editing + "editingSpecialization.fxml", "Редактирование специализации"),
        EDITING_COMMAND(way + editing + "editingCommand.fxml", "Редактирование команлы"),
        EDITING_PERMISSION(way + editing + "editingPermission.fxml", "Редактирование прав"),

        DELETE_MATCH(way + deleting + "deletingMatch.fxml", "Удаление матча"),
        DELETE_SPECIALIZATION(way + deleting + "deletingSpecialization.fxml", "Удаление специализации"),
        DELETE_COMMAND(way + deleting + "deletingCommand.fxml", "Удаление команды"),

        MAIN(way + "main.fxml", "Главное меню");

        private final String path;
        private final String title;

        EScene(String name, String title) {
            this.path = name;
            this.title = title;
        }

        public String getPath() {
            return path;
        }
    }

    public static FXMLLoader loadScene(EScene scene) {
        FXMLLoader loader = getLoader(scene);
        Parent root = getRoot(loader);
        loadStage(root, scene.title).show();
        return loader;
    }

    public static FXMLLoader loadMainSceneWithUserCallBack(EScene scene, User user) {
        FXMLLoader loader = getLoader(scene);
        Parent root = getRoot(loader);
        loadMainStage(root, scene.title, user).show();
        return loader;
    }

    public static void hideScene(Node node) {
        node.getScene().getWindow().hide();
    }

    private static FXMLLoader getLoader(EScene scene) {
        return new FXMLLoader(Main.class.getResource(scene.getPath()));
    }

    private static Parent getRoot(FXMLLoader loader) {
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }

    private static Stage loadStage(Parent root, String title) {
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle(title);
        return stage;
    }

    private static Stage loadMainStage(Parent root, String title, User user) {
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle(title);
        stage.setOnCloseRequest(ev -> {
            MainController controller = ViewLoader.loadScene(EScene.MAIN).getController();
            controller.init(user);
        });
        return stage;
    }

}
