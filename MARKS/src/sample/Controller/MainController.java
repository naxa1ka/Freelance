package sample.Controller;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import sample.Alerts;
import sample.FileSystem.FileManager;
import sample.Loader.SceneView;
import sample.Loader.ViewLoader;
import sample.Model.Model.CollectionModel;
import sample.Model.Beans.*;
import sample.Model.Model.ListCollectionModel;
import sample.Model.Model.MarkModel;
import sample.Model.Model.SerieModel;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController extends BaseController implements Initializable {
    @FXML
    private TableView<ListCollection> table;

    @FXML
    private TableColumn<ListCollection, Integer> page;

    @FXML
    private TableColumn<ListCollection, Serie> serie;

    @FXML
    private TableColumn<ListCollection, Country> countryMark;

    @FXML
    private ComboBox<Collection> collectionComboBox;

    @FXML
    private ComboBox<Mark> markComboBox;

    @FXML
    private ComboBox<Serie> serieComboBox;

    @FXML
    private Label pagesInCollections;

    CollectionModel collectionModel = new CollectionModel();
    ListCollectionModel listCollectionModel = new ListCollectionModel();
    MarkModel markModel = new MarkModel();
    SerieModel serieModel = new SerieModel();

    Collection currentCollection = null;
    Serie currentSerie = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
        updateItems();
    }

    private void initTable() {
        page.setCellValueFactory(cellData -> cellData.getValue().pageProperty().asObject());
        serie.setCellValueFactory(cellData -> cellData.getValue().getMark().serieProperty());
        countryMark.setCellValueFactory(cellData -> cellData.getValue().getMark().countryProperty());
    }

    private void updateTable() {
        int serieID;
        if (currentSerie == null) {
            serieID = -1;
        } else {
            serieID = currentSerie.getId();
        }
        table.setItems(listCollectionModel.getListCollections(
                currentCollection.getId(), serieID
        ));
    }

    private void initMarkComboBox() {
        markModel.initComboBox(markComboBox);
    }

    private void initSerieComboBox() {
        serieComboBox.setItems(serieModel.getList());
    }

    private void initCollectionComboBox() {
        collectionComboBox.setItems(collectionModel.getList());
        if (!collectionComboBox.getItems().isEmpty()) {
            Collection collection = collectionComboBox.getItems().get(0);
            currentCollection = collection;
            collectionComboBox.setValue(collection);
        }

    }

    private void initPages() {
        if (table.getItems().isEmpty()) {
            pagesInCollections.setText("Пусто");
            return;
        }

        pagesInCollections.setText(
                String.valueOf(listCollectionModel.getAmountPages(currentCollection)
                ));

    }

    @FXML
    void onAddCountry() {
        loadScene(SceneView.ADD_COUNTRY);
    }

    @FXML
    void onAddMark() {
        loadScene(SceneView.ADD_MARK);
    }

    @FXML
    void onAddSerie() {
        loadScene(SceneView.ADD_SERIE);
    }

    @FXML
    void onAddCollection() {
        loadScene(SceneView.ADD_COLLECTION);
    }

    /***************************************************/

    @FXML
    void onEditCountry() {
        loadScene(SceneView.EDIT_COUNTRY);
    }

    @FXML
    void onEditMark() {
        loadScene(SceneView.EDIT_MARK);
    }

    @FXML
    void onEditSerie() {
        loadScene(SceneView.EDIT_SERIE);
    }

    @FXML
    void onEditCollection() {
        loadScene(SceneView.EDIT_COLLECTION);
    }

    /***************************************************/

    @FXML
    void onDeleteCountry() {
        loadScene(SceneView.DELETE_COUNTRY);
    }

    @FXML
    void onDeleteMark() {
        loadScene(SceneView.DELETE_MARK);
    }

    @FXML
    void onDeleteSerie() {
        loadScene(SceneView.DELETE_SERIE);
    }

    @FXML
    void onDeleteCollection() {
        loadScene(SceneView.DELETE_COLLECTION);
    }

    /***************************************************/

    @FXML
    void onHiddenCollections() {
        currentCollection = collectionComboBox.getValue();
        updateTable();
        initPages();
    }

    @FXML
    void onAddCollectionMark() {
        if (listCollectionModel.addMark(
                collectionComboBox.getValue(),
                markComboBox.getValue())
        ) {
            alertInfo("Успешно добавлено!");
            updateTable();
        } else {
            alertWarning("Марка уже есть в коллекции!");
        }
    }

    @FXML
    void onDelCollectionMark() {
        Mark selectedMark;
        if (table.getSelectionModel().isEmpty()) {
            selectedMark = markComboBox.getValue();
        } else {
            selectedMark = table.getSelectionModel().getSelectedItem().getMark();
        }

        if (listCollectionModel.deleteMark(
                collectionComboBox.getValue(),
                selectedMark
        )) {
            alertInfo("Успешно удалено!");
        } else {
            alertWarning("Такой марки в коллекции нет!");
        }

        updateTable();
    }

    @FXML
    void onUpdatePressed() {
        updateItems();
    }

    @FXML
    void onResetSerie() {
        serieComboBox.getSelectionModel().clearSelection();
        currentSerie = null;
        updateTable();
    }

    @FXML
    void onChangedSerie() {
        currentSerie = serieComboBox.getValue();
        updateTable();
    }

    private void updateItems() {
        initMarkComboBox();
        initSerieComboBox();
        initCollectionComboBox();
        updateTable();
        initPages();
    }

    @FXML
    void onListCountry() {
        ListCountryController listCountryController =
                ViewLoader.loadScene(SceneView.LIST_COUNTRY).getController();
        listCountryController.init(
                listCollectionModel.getCountryListInCollection(currentCollection)
        );
    }

    @FXML
    void onOpenLogs() {
        try {
            FileManager.openLogFile();
        } catch (IOException e) {
            Alerts.alertWarning("Ошибка с логами!");
            e.printStackTrace();
        }
    }

    @FXML
    void onOpenHtml() {
        FileManager.loadHTML(FileManager.fileOpening(), collectionComboBox.getValue());
        updateTable();
    }

    @FXML
    void onOpenPdf() {
        FileManager.loadPDF(FileManager.fileOpening(), collectionComboBox.getValue());
        updateTable();
    }

    @FXML
    void onOpenTxt() {
        FileManager.loadTXT(FileManager.fileOpening(), collectionComboBox.getValue());
        updateTable();
    }

    @FXML
    void onOpenXml() {
        FileManager.loadXML(FileManager.fileOpening(), collectionComboBox.getValue());
        updateTable();
    }

    @FXML
    void onSaveHtml() {
        FileManager.saveHTML(FileManager.htmlChooser(), collectionComboBox.getValue());
    }

    @FXML
    void onSavePdf() {
        FileManager.savePDF(FileManager.pdfChooser(), collectionComboBox.getValue());
    }

    @FXML
    void onSaveTxt() {
        FileManager.saveTXT(FileManager.txtChooser(), collectionComboBox.getValue());
    }

    @FXML
    void onSaveXml() {
        FileManager.saveXML(FileManager.xmlChooser(), collectionComboBox.getValue());
    }
}
