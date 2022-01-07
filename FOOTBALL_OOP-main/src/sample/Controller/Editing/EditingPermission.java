package sample.Controller.Editing;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import sample.DB.DbHandler;
import sample.DB.Permission;
import sample.Utils.Alerts;
import sample.Utils.Utils;

public class EditingPermission {

    @FXML
    private CheckBox addSpec;

    @FXML
    private CheckBox addCom;

    @FXML
    private CheckBox addFoot;

    @FXML
    private CheckBox delSpec;

    @FXML
    private CheckBox delCom;

    @FXML
    private CheckBox delFoot;

    @FXML
    private CheckBox editSpec;

    @FXML
    private CheckBox editCom;

    @FXML
    private CheckBox editFoot;

    @FXML
    private CheckBox save;

    @FXML
    private CheckBox upload;

    @FXML
    private CheckBox givePerm;

    @FXML
    private CheckBox openLogging;

    @FXML
    private CheckBox addMatch;

    @FXML
    private TextField loginField;

    DbHandler db = DbHandler.getInstance();

    @FXML
    void onGiveButtonPressed() {
        if (Utils.isEmpty(loginField)){
            Alerts.alertWarning("Ввод", "Заполните все поля");
            return;
        }

        if (!db.isExistUser(loginField.getText().trim())) {
            Alerts.alertWarning("БД", "Пользователь отсутствует!");
            return;
        }

        db.updatePermission(loginField.getText().trim(), getMask());
        Alerts.alertInfo("Успех", "Изменено!");
    }

    private int getMask(){
        int mask = 0;

        if (addSpec.isSelected())
            mask |= db.getMask(Permission.ADD_SPEC);
        if (addCom.isSelected())
            mask |= db.getMask(Permission.ADD_COMM);
        if (addFoot.isSelected())
            mask |= db.getMask(Permission.ADD_FOOT);
        if (addMatch.isSelected())
            mask |= db.getMask(Permission.ADD_MATCH);

        if (delCom.isSelected())
            mask |= db.getMask(Permission.DELETE_COMM);
        if (delFoot.isSelected())
            mask |= db.getMask(Permission.DELETE_FOOT);
        if (delSpec.isSelected())
            mask |= db.getMask(Permission.DELETE_SPEC);

        if (editCom.isSelected())
            mask |= db.getMask(Permission.EDIT_COOM);
        if (editFoot.isSelected())
            mask |= db.getMask(Permission.EDIT_FOOT);
        if (editSpec.isSelected())
            mask |= db.getMask(Permission.EDIT_SPEC);

        if (save.isSelected())
            mask |= db.getMask(Permission.SAVE);
        if (upload.isSelected())
            mask |= db.getMask(Permission.UPLOAD);
        if (givePerm.isSelected())
            mask |= db.getMask(Permission.GIVE_PERM);
        if (openLogging.isSelected())
            mask |= db.getMask(Permission.OPEN_LOGGING);

        return mask;
    }
}


