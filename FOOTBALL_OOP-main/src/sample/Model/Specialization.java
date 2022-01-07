package sample.Model;

public class Specialization {
    private int id;
    private String role;

    public Specialization(){}

    public Specialization(int id, String role) {
        this.id = id;
        this.role = role;
    }

    public Specialization(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return role;
    }
}
