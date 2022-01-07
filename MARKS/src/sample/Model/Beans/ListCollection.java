package sample.Model.Beans;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Objects;

public class ListCollection extends Bean {
    private IntegerProperty id;
    private IntegerProperty page;
    private ObjectProperty<Mark> mark;

    public ListCollection(int id, int page, Mark mark) {
        this.id = new SimpleIntegerProperty(id);
        this.page = new SimpleIntegerProperty(page);
        this.mark = new SimpleObjectProperty<>(mark);
    }

    public ListCollection(){
        this.id = new SimpleIntegerProperty();
        this.page = new SimpleIntegerProperty();
        this.mark = new SimpleObjectProperty<>();
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getPage() {
        return page.get();
    }

    public IntegerProperty pageProperty() {
        return page;
    }

    public void setPage(int page) {
        this.page.set(page);
    }

    public Mark getMark() {
        return mark.get();
    }

    public ObjectProperty<Mark> markProperty() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark.set(mark);
    }

    public ListCollection(int page, Mark mark) {
        this.page = new SimpleIntegerProperty(page);
        this.mark = new SimpleObjectProperty<>(mark);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListCollection that = (ListCollection) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(mark, that.mark);
    }

    @Override
    public String toString() {
        return page.get() + " | " + mark.get().getId() + " | " + mark.get().getSerie().getId() +
                " | " + mark.get().getSerie().getName() + " | " + mark.get().getSerie().getYear() + " | " +
                mark.get().getCountry().getId() + " | "  + mark.get().getCountry().getName();
    }
}
