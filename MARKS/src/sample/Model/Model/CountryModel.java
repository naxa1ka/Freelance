package sample.Model.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import sample.Model.Beans.Country;

public class CountryModel extends BaseModel<Country> {
    @Override
    public void initList() {
        list = FXCollections.observableArrayList(db.getAllCountry());
    }

    @Override
    protected void setList(Country oldItem, Country newItem) {
        list.set(
                list.indexOf(oldItem),
                new Country(oldItem.getId(), newItem.getName())
        );
    }
}
