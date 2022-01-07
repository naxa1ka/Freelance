package sample.Model.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import sample.Model.Beans.Mark;

public class MarkModel  extends BaseModel<Mark> {
    @Override
    protected void setList(Mark oldItem, Mark newItem) {
        list.set(
                list.indexOf(oldItem),
                new Mark(oldItem.getId(), newItem.getCountry(), newItem.getSerie())
        );
    }

    @Override
    public void initList(){
        list  = FXCollections.observableArrayList(db.getAllMark());
    }
}
