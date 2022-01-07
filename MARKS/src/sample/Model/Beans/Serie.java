package sample.Model.Beans;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Serie extends Bean {
    private IntegerProperty id;
    private final StringProperty name;
    private final IntegerProperty year;

    public Serie(int id, String name, int year) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.year = new SimpleIntegerProperty(year);
    }

    public Serie() {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.year = new SimpleIntegerProperty();
    }

    public Serie(String name, int year) {
        this.name = new SimpleStringProperty(name);
        this.year = new SimpleIntegerProperty(year);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public IntegerProperty yearProperty() {
        return year;
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getYear() {
        return year.get();
    }

    public void setYear(int year) {
        this.year.set(year);
    }

    @Override
    public String toString() {
        return year.get() + " " + name.get();
    }
}
