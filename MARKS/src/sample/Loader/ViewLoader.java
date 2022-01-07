package sample.Loader;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Main;

import java.io.IOException;

public class ViewLoader {
    public static FXMLLoader loadScene(SceneView sceneView) {
        FXMLLoader loader = getLoader(sceneView);
        Parent root = getRoot(loader);
        loadStage(root, sceneView.getTitle()).show();
        return loader;
    }

    public static void hideScene(Node node) {
        node.getScene().getWindow().hide();
    }

    private static FXMLLoader getLoader(SceneView sceneView) {
        return new FXMLLoader(Main.class.getResource(sceneView.getPath()));
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


}
