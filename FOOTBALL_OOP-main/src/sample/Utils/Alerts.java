package sample.Utils;

import javafx.scene.control.Alert;

public class Alerts {
    private static void alertBase(Alert alert, String header, String content) {
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();
    }

    public static void alertInfo(String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Информация!");
        alertBase(alert, header, content);
    }

    public static void alertError(String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка!");
        alertBase(alert, header, content);
    }

    public static void alertWarning(String header, String content){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Предупреждение!");
        alertBase(alert, header, content);
    }
}
