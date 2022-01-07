package sample.Controller;

import com.sun.javafx.scene.control.skin.DatePickerSkin;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import org.apache.log4j.Logger;
import sample.Controller.Editing.EditingFootballer;
import sample.DB.DbHandler;
import sample.DB.Permission;
import sample.Utils.Alerts;
import sample.Utils.FileManager;
import sample.Model.*;
import sample.Utils.ViewLoader;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class MainController implements Initializable {
    private static final Logger log = Logger.getLogger(MainController.class);

    @FXML
    private Menu loggerMenu;

    @FXML
    private Menu saveMenu;

    @FXML
    private Menu uploadMenu;

    @FXML
    private Menu addMenuParent;

    @FXML
    private Menu editMenuParent;

    @FXML
    private Menu delMenuParent;

    @FXML
    private MenuItem addFootballMenu;

    @FXML
    private MenuItem addSpecMenu;

    @FXML
    private MenuItem addMatchMenu;

    @FXML
    private MenuItem addCommandMenu;

    @FXML
    private MenuItem editFootball;

    @FXML
    private MenuItem editCommand;

    @FXML
    private MenuItem editSpec;

    @FXML
    private MenuItem deleteFootball;

    @FXML
    private MenuItem deleteSpec;

    @FXML
    private MenuItem deleteCommand;

    @FXML
    private MenuItem permissionMenu;

    @FXML
    private Menu actionsMenu;

    @FXML
    private TableView<Footballer> table;

    @FXML
    private TableColumn<Footballer, Integer> id;

    @FXML
    private TableColumn<Footballer, String> name;

    @FXML
    private TableColumn<Footballer, Specialization> specialization;

    @FXML
    private TableColumn<Footballer, Integer> allGames;

    @FXML
    private TableColumn<Footballer, Integer> successGames;

    @FXML
    private TableColumn<Footballer, Command> command;

    DbHandler db = DbHandler.getInstance();

    User currentUser;

    public void init(User user) {
        currentUser = db.getUser(user.getLogin(), user.getPassword());
        initVisibleMenu(currentUser);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
        updateTable();
    }

    private void initTable() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        specialization.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        allGames.setCellValueFactory(new PropertyValueFactory<>("allGames"));
        successGames.setCellValueFactory(new PropertyValueFactory<>("successGames"));
        command.setCellValueFactory(new PropertyValueFactory<>("command"));
    }

    private void updateTable() {
        table.setItems(db.getObservableListOfFootballers());
    }

    private void initVisibleMenu(User user) {
        int mask = user.getMask();

        addCommandMenu.setVisible(db.isCan(mask, Permission.ADD_COMM));
        addFootballMenu.setVisible(db.isCan(mask, Permission.ADD_FOOT));
        addSpecMenu.setVisible(db.isCan(mask, Permission.ADD_SPEC));
        addMatchMenu.setVisible(db.isCan(mask, Permission.ADD_MATCH));

        editCommand.setVisible(db.isCan(mask, Permission.EDIT_COOM));
        editFootball.setVisible(db.isCan(mask, Permission.EDIT_FOOT));
        editSpec.setVisible(db.isCan(mask, Permission.EDIT_SPEC));

        deleteCommand.setVisible(db.isCan(mask, Permission.DELETE_COMM));
        deleteFootball.setVisible(db.isCan(mask, Permission.DELETE_FOOT));
        deleteSpec.setVisible(db.isCan(mask, Permission.DELETE_SPEC));

        saveMenu.setVisible(db.isCan(mask, Permission.SAVE));
        uploadMenu.setVisible(db.isCan(mask, Permission.UPLOAD));

        loggerMenu.setVisible(db.isCan(mask, Permission.OPEN_LOGGING));
        permissionMenu.setVisible(db.isCan(mask, Permission.GIVE_PERM));

        if (!addCommandMenu.isVisible() && !addFootballMenu.isVisible() && !addSpecMenu.isVisible() && !addMatchMenu.isVisible())
            addMenuParent.setVisible(false);

        if (!editSpec.isVisible() && !editFootball.isVisible() && !editCommand.isVisible())
            editMenuParent.setVisible(false);

        if (!deleteSpec.isVisible() && !deleteFootball.isVisible() && !deleteCommand.isVisible())
            delMenuParent.setVisible(false);

        if (!permissionMenu.isVisible() && !addMenuParent.isVisible() &&
                !editMenuParent.isVisible() && !delMenuParent.isVisible())
            actionsMenu.setVisible(false);
    }

    @FXML
    void onAddCommandPressed() {
        log.info("Добавить команду нажато: " + currentUser.getLogin());
        ViewLoader.loadMainSceneWithUserCallBack(ViewLoader.EScene.ADDING_COMMAND, currentUser);
        ViewLoader.hideScene(table);
    }

    @FXML
    void onAddMatchPressed() {
        log.info("Добавить матч нажато: " + currentUser.getLogin());
        ViewLoader.loadMainSceneWithUserCallBack(ViewLoader.EScene.ADDING_MATCH, currentUser);
        ViewLoader.hideScene(table);
    }

    @FXML
    void onAddFootballPressed() {
        log.info("Добавить футболиста нажато: " + currentUser.getLogin());
        ViewLoader.loadMainSceneWithUserCallBack(ViewLoader.EScene.ADDING_FOOTBALLER, currentUser);
        ViewLoader.hideScene(table);
    }

    @FXML
    void onAddSpecPressed() {
        log.info("Добавить специалиста нажато: " + currentUser.getLogin());
        ViewLoader.loadMainSceneWithUserCallBack(ViewLoader.EScene.ADDING_SPECIALIZATION, currentUser);
        ViewLoader.hideScene(table);
    }

    @FXML
    void onDeleteCommand() {
        log.info("Удалить команду нажато: " + currentUser.getLogin());
        ViewLoader.loadMainSceneWithUserCallBack(ViewLoader.EScene.DELETE_COMMAND, currentUser);
        ViewLoader.hideScene(table);
    }

    @FXML
    void onDeleteFootball() {
        if (table.getSelectionModel().isEmpty()) {
            Alerts.alertWarning("Удаление", "Не выбран футболист для удаления!");
            log.warn("Удаление футболиста: не выбрано " + currentUser.getLogin());
            return;
        }

        log.info("Удаление футболиста нажато: " + currentUser.getLogin());
        db.deleteFootballer(table.getSelectionModel().getSelectedItem().getId());
        updateTable();
    }

    @FXML
    void onDeleteSpec() {
        log.info("Удаление cпециализации нажато: " + currentUser.getLogin());
        ViewLoader.loadMainSceneWithUserCallBack(ViewLoader.EScene.DELETE_SPECIALIZATION, currentUser);
        ViewLoader.hideScene(table);
    }

    @FXML
    void onEditFootball() {
        if (table.getSelectionModel().isEmpty()) {
            Alerts.alertWarning("Редактирование", "Не выбран футболист для редактирования!");
            log.warn("Редактирование футболиста: не выбрано " + currentUser.getLogin());
            return;
        }
        log.info("Редактирование футболиста нажато: " + currentUser.getLogin());
        EditingFootballer controller = ViewLoader
                .loadMainSceneWithUserCallBack(ViewLoader.EScene.EDITING_FOOTBALLER, currentUser)
                .getController();
        controller.init(table.getSelectionModel().getSelectedItem());
        ViewLoader.hideScene(table);
    }

    @FXML
    void onEditCommand() {
        log.info("Редактирование команды нажато: " + currentUser.getLogin());
        ViewLoader.loadMainSceneWithUserCallBack(ViewLoader.EScene.EDITING_COMMAND, currentUser);
        ViewLoader.hideScene(table);
    }

    @FXML
    void onEditSpec() {
        log.info("Редактирование cпециализации нажато: " + currentUser.getLogin());
        ViewLoader.loadMainSceneWithUserCallBack(ViewLoader.EScene.EDITING_SPECIALIZATION, currentUser);
        ViewLoader.hideScene(table);
    }

    @FXML
    void onPermissionPressed() {
        log.info("Редактирование прав нажато: " + currentUser.getLogin());
        ViewLoader.loadMainSceneWithUserCallBack(ViewLoader.EScene.EDITING_PERMISSION, currentUser);
        ViewLoader.hideScene(table);
    }

    @FXML
    void onOpenTXT() {
        log.info("Открытие txt нажато: " + currentUser.getLogin());
        File file = FileManager.fileOpening();
        FileManager.loadTXT(file);
        updateTable();
    }

    @FXML
    void onOpenXML() {
        log.info("Открытие xml нажато: " + currentUser.getLogin());
        File file = FileManager.fileOpening();
        FileManager.loadXML(file);
        updateTable();
    }

    @FXML
    void onOpenHTML() {
        log.info("Открытие html нажато: " + currentUser.getLogin());
        File file = FileManager.fileOpening();
        FileManager.loadHTML(file);
        updateTable();
    }

    @FXML
    void onOpenPDF() {
        log.info("Открытие pdf нажато: " + currentUser.getLogin());
        File file = FileManager.fileOpening();
        FileManager.loadPDF(file);
        updateTable();
    }

    @FXML
    void onSaveTXT() {
        log.info("Сохранение txt нажато: " + currentUser.getLogin());
        File file = FileManager.txtChooser();
        FileManager.saveTXT(file);
    }

    @FXML
    void onSaveXML() {
        log.info("Сохранение xml нажато: " + currentUser.getLogin());
        File file = FileManager.xmlChooser();
        FileManager.saveXML(file);
    }

    @FXML
    void onSaveHTML() {
        log.info("Сохранение html нажато: " + currentUser.getLogin());
        File file = FileManager.htmlChooser();
        FileManager.saveHTML(file);
    }

    @FXML
    void onSavePDF() {
        log.info("Сохранение pdf нажато: " + currentUser.getLogin());
        File file = FileManager.pdfChooser();
        FileManager.savePDF(file);
    }
    @FXML
    void onExit() {
        log.info("Выход: " + currentUser.getLogin());
        ViewLoader.loadScene(ViewLoader.EScene.ENTER);
        ViewLoader.hideScene(table);
    }


    @FXML
    void onClickCalendar() {
        log.info("Открытие календаря: " + currentUser.getLogin());
        ViewLoader.loadScene(ViewLoader.EScene.CALENDAR);
    }

    @FXML
    void onOpenLoggerPressed() {
        log.info("Открытие логов: " + currentUser.getLogin());
        try {
            FileManager.openLogFile();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Ошибка открытия логов!",e);
            Alerts.alertError("Логгер", "Ошибка открытия логов!");
        }
    }
}