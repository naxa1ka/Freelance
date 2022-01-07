package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import sample.Utils.ViewLoader;

import java.io.IOException;

public class Main extends Application {
    private static final Logger log = Logger.getLogger(Main.class);

    @Override
    public void start(Stage primaryStage) throws IOException {
        //ViewLoader.loadScene(ViewLoader.EScene.ENTER);

        Parent root = FXMLLoader.load(getClass().getResource("View/Auth/login.fxml"));
        primaryStage.setTitle("Авторизация");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        log.info("Приложение запущено!");
        launch(args);
    }
}
