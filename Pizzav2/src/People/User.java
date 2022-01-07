package People;

public class User {
    private String login;
    private String password;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean enter(User user) {
        return (this.password.equals(user.password) && this.login.equals(user.login));
    }

    public boolean enter(String login, String password) {
        return (this.login.equals(login) && this.password.equals(password));
    }
}

