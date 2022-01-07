package sample.Model.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import sample.Model.Beans.Collection;

public class CollectionModel extends BaseModel<Collection> {
    @Override
    public void initList() {
        list = FXCollections.observableArrayList(db.getAllCollection());
    }

    @Override
    protected void setList(Collection oldCollection, Collection newCollection) {
        list.set(
                list.indexOf(oldCollection),
                new Collection(oldCollection.getId(), newCollection.getName())
        );
    }
}
