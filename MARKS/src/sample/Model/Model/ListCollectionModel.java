package sample.Model.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Model.Beans.Collection;
import sample.Model.Beans.Country;
import sample.Model.Beans.ListCollection;
import sample.Model.Beans.Mark;

import java.util.List;

public class ListCollectionModel extends BaseModel<ListCollection> {
    private ObservableList<ListCollection> listCollections  = FXCollections.observableArrayList();

    public ObservableList<ListCollection> getListCollections(int id, int serieID) {
        initList(id, serieID);
        return listCollections;
    }

    public boolean updateMark(ListCollection oldList, ListCollection newList){
       // if (db.isExistListCollection(newList)) return false;

        db.updateListCollection(oldList, newList);
        listCollections.set(
                listCollections.indexOf(oldList),
                new ListCollection(oldList.getId(), newList.getPage(), newList.getMark()
        ));
        return true;
    }

    public boolean deleteMark(Collection collection, Mark mark){
        if (!db.isExist(collection, mark)) return false;

        listCollections.remove(
                new ListCollection(
                        collection.getId(),
                        mark
                )
        );
        db.deleteListCollection(collection, mark);
        return true;
    }

    public List<Country> getCountryListInCollection(Collection collection){
        return db.getCountryListInCollection(collection);
    }

    public int getAmountPages(Collection collection){
        return db.countPages(collection);
    }

    public boolean addMark(Collection collection, Mark mark){
        if (db.isExist(collection, mark)) return false;

        db.addListCollection(collection, mark);
        listCollections.add(new ListCollection(
                collection.getId(),
                db.countPages(collection),
                mark
        ));
        return true;
    }

    private void initList(int id, int serieID){
        listCollections = FXCollections.observableArrayList(db.getAllListCollection(id, serieID));
    }


    @Override
    protected void initList() {

    }

    @Override
    protected void setList(ListCollection oldItem, ListCollection newItem) {

    }
}
