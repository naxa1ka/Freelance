package sample.Model.Model;

import sample.DB.SqliteHandler;
import sample.Model.Beans.User;

public class UserModel {
    SqliteHandler db = SqliteHandler.getInstance();
    public User login(String login, String password) {
        return db.getUser(login, password);
    }

    public boolean register(User user){
        if (db.isExist(user)) return false;

        db.addUser(user);
        return true;
    }
}
