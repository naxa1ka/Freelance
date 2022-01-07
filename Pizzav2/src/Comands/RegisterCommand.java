package Comands;

import District.DistrictHandler;
import People.Client;
import People.User;

public class RegisterCommand extends Command {
    @Override
    public String getCommandName() {
        return "reg";
    }

    @Override
    public void execute() throws ExitException {
        System.out.println("Введите логин:");
        input();
        System.out.println("Введите пароль:");
        input();
        System.out.println("Введите  название района");
        input();
    }
}
