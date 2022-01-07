package sample.Model.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import sample.DB.SqliteHandler;
import sample.Model.Beans.Bean;

public abstract class BaseModel<T extends Bean> {
    protected ObservableList<T> list = FXCollections.observableArrayList();

    protected abstract void initList();

    protected abstract void setList(T oldItem, T newItem);

    public ObservableList<T> getList() {
        initList();
        return list;
    }

    public boolean add(T item) {
        if (db.isExist(item)) return false;

        db.add(item);
        list.add(item);
        return true;
    }

    public void delete(T item) {
        db.delete(item);
        list.remove(item);
    }

    public boolean update(T oldItem, T newItem) {
        if (db.isExist(newItem)) return false;

        db.updateCountry(oldItem, newItem);
        setList(oldItem, newItem);
        return true;
    }


    public void initComboBox(ComboBox<T> comboBox) {
        comboBox.setItems(getList());
        if (!comboBox.getItems().isEmpty()) {
            comboBox.setValue(comboBox.getItems().get(0));
        }
    }

    protected SqliteHandler db = SqliteHandler.getInstance();
}
