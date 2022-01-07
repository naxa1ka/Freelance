package sample.Model.Beans;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Mark extends Bean {
    private IntegerProperty id;
    private ObjectProperty<Country> country;
    private ObjectProperty<Serie> serie;

    public Mark(int id, Country country, Serie serie) {
        this.id = new SimpleIntegerProperty(id);
        this.country = new SimpleObjectProperty<>(country);
        this.serie = new SimpleObjectProperty<>(serie);
    }

    public Mark(Country country, Serie serie) {
        this.country = new SimpleObjectProperty<>(country);
        this.serie = new SimpleObjectProperty<>(serie);
    }

    public Mark(){
        this.id = new SimpleIntegerProperty();
        this.country = new SimpleObjectProperty<>();
        this.serie = new SimpleObjectProperty<>();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public ObjectProperty<Country> countryProperty() {
        return country;
    }

    public ObjectProperty<Serie> serieProperty() {
        return serie;
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public Country getCountry() {
        return country.get();
    }

    public void setCountry(Country country) {
        this.country.set(country);
    }

    public Serie getSerie() {
        return serie.get();
    }

    public void setSerie(Serie serie) {
        this.serie.set(serie);
    }

    @Override
    public String toString() {
        return  serie.get().toString() + " " + country.get().toString();
    }
}
