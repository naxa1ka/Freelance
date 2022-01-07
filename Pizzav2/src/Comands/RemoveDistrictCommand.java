package Comands;

import District.DistrictHandler;

public class RemoveDistrictCommand extends Command{
    @Override
    public String getCommandName() {
        return "removedist";
    }

    @Override
    public void execute() throws ExitException {

        System.out.println();

        System.out.println("Введите краткое название района");
        input();
    }
}
