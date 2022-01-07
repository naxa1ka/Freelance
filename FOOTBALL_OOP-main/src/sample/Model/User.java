package sample.Model;

public class User {
    private String login;
    private String password;
    private String firstName;
    private int mask;

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getMask() {
        return mask;
    }

    public void setMask(int mask) {
        this.mask = mask;
    }

    public User(String login, String password, String firstName, int mask) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.mask = mask;
    }

    public User(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.mask = 0;
    }
}
