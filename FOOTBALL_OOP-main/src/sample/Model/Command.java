package sample.Model;

import java.util.Objects;

public class Command {
    private int id;
    private String name;

    public Command(){}

    public Command(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Command(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Command command = (Command) o;
        return Objects.equals(name, command.name);
    }

    @Override
    public String toString() {
        return name;
    }
}
