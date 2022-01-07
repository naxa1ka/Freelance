package sample.Utils;

import javafx.scene.control.TextField;

public class Utils {

    public static boolean isEmpty(TextField ... textFields){
        for (TextField tf: textFields) {
            if (tf.getText().trim().equals("")){
                return true;
            }
        }
        return false;
    }

}
