package sample.Model.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import sample.Model.Beans.Serie;

public class SerieModel extends BaseModel<Serie> {
    @Override
    protected void setList(Serie oldItem, Serie newItem) {
        list.set(
                list.indexOf(oldItem),
                new Serie(oldItem.getId(), newItem.getName(), newItem.getYear())
        );
    }

    public void initList(){
        list = FXCollections.observableArrayList(db.getAllSerie());
    }

}
