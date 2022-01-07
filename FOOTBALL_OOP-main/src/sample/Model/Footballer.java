package sample.Model;

public class Footballer {
    private int id;
    private String name;
    private Specialization specialization;
    private int allGames;
    private int successGames;
    private Command command;

    public Footballer() {

    }

    public Footballer(int id, String name, Specialization specialization, int allGames, int successGames, Command command) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.allGames = allGames;
        this.successGames = successGames;
        this.command = command;
    }

    public Footballer(String name, Specialization specialization, int allGames, int successGames, Command command) {
        this.name = name;
        this.specialization = specialization;
        this.allGames = allGames;
        this.successGames = successGames;
        this.command = command;
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

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public int getAllGames() {
        return allGames;
    }

    public void setAllGames(int allGames) {
        this.allGames = allGames;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public int getSuccessGames() {
        return successGames;
    }

    public void setSuccessGames(int successGames) {
        this.successGames = successGames;
    }

    @Override
    public String toString() {
        return name + " | " +
                specialization.getId() + " | " +
                specialization.getRole() + " | " +
                command.getId() + " | " +
                command.getName() + " | " +
                successGames + " | " +
                allGames + " | ";
    }
}
