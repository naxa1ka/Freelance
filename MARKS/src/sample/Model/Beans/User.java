package sample.Model.Beans;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {
    private IntegerProperty id;
    private final StringProperty login;
    private final StringProperty password;

    public User(int id, String login, String password) {
        this.id = new SimpleIntegerProperty(id);
        this.login = new SimpleStringProperty(login);
        this.password = new SimpleStringProperty(password);
    }

    public User(String login, String password) {
        this.login = new SimpleStringProperty(login);
        this.password = new SimpleStringProperty(password);
    }

    public User() {
        this(null, null);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getLogin() {
        return login.get();
    }

    public void setLogin(String login) {
        this.login.set(login);
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    @Override
    public String toString() {
       return login.get();
    }
}
