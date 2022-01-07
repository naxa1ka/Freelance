package sample;
import javafx.scene.control.Alert;
import org.apache.log4j.Logger;

public class Alerts {
    private static final Logger log = Logger.getLogger(Alerts.class);
    private static void alertBase(Alert alert, String header, String content) {
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();
    }

    public static void alertInfo(String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Информация!");
        alertBase(alert, "Информация", content);
        log.info(content);
    }

    public static void alertError(String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка!");
        alertBase(alert, "Что-то пошло не так...", content);
        log.error(content);
    }

    public static void alertWarning(String content){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Предупреждение!");
        alertBase(alert, "Будьте аккуратнее", content);
        log.warn(content);
    }
}