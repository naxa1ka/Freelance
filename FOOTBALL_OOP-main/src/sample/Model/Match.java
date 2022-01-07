package sample.Model;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Match {
    private int id;
    private Command firstCommand;
    private Command secondCommand;
    private LocalDate date;

    public Match(Command firstCommand, Command secondCommand, LocalDate date) {
        this.firstCommand = firstCommand;
        this.secondCommand = secondCommand;
        this.date = date;
    }

    public Match(int id, Command firstCommand, Command secondCommand, LocalDate date) {
        this.id = id;
        this.firstCommand = firstCommand;
        this.secondCommand = secondCommand;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;
        return Objects.equals(firstCommand, match.firstCommand) && Objects.equals(secondCommand, match.secondCommand) && Objects.equals(date, match.date);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Command getFirstCommand() {
        return firstCommand;
    }

    public void setFirstCommand(Command firstCommand) {
        this.firstCommand = firstCommand;
    }

    public Command getSecondCommand() {
        return secondCommand;
    }

    public void setSecondCommand(Command secondCommand) {
        this.secondCommand = secondCommand;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
