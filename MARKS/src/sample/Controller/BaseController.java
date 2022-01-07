package sample.Controller;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import sample.Alerts;
import sample.Loader.SceneView;
import sample.Loader.ViewLoader;

public abstract class BaseController {

    protected void alertInfo(String content) {
        Alerts.alertInfo(content);
    }

    protected void alertError(String content) {
        Alerts.alertError(content);
    }

    protected void alertWarning(String content) {
        Alerts.alertWarning(content);
    }

    protected boolean isEmpty(TextField... textFields) {
        for (TextField tf : textFields) {
            if (tf.getText().trim().equals("")) {
                return true;
            }
        }
        return false;
    }

    protected void loadScene(SceneView scene) {
        ViewLoader.loadScene(scene);
    }

    protected void hideScene(Node node) {
        ViewLoader.hideScene(node);
    }
}
