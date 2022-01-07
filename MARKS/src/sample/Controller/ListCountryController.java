package sample.Controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import sample.Model.Beans.Country;

import java.util.List;

public class ListCountryController {

    @FXML
    private TableView<Country> table;

    @FXML
    private TableColumn<Country, String> nameCountry;

    private List<Country> countries;

    public void init(List<Country> countryList){
        countries = countryList;
        initTable();
        updateItems();
    }


    private void updateItems() {
        table.setItems(FXCollections.observableArrayList(countries));
    }

    private void initTable() {
        nameCountry.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
    }

}