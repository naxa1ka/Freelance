package Comands;

import District.DistrictHandler;
import People.User;

public class EnterCommand extends Command {
    @Override
    public String getCommandName() {
        return "enter";
    }

    @Override
    public void execute() throws ExitException {
        System.out.println("Введите логин:");
        input();
        System.out.println("Введите пароль:");
        input();
    }
}
