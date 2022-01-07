package Comands;

import District.DistrictHandler;

public class RemoveCourierCommand extends Command{
    @Override
    public String getCommandName() {
        return "removecour";
    }

    @Override
    public void execute() throws ExitException {

        System.out.println();

        System.out.println("Введите краткое название района");
        input();
    }
}
