package Comands;

import District.DistrictHandler;

public class AddCourierCommand extends Command{
    @Override
    public String getCommandName() {
        return "addcour";
    }

    @Override
    public void execute() throws ExitException {

        System.out.println();

        System.out.println("Введите краткое название района");
        input();
    }
}
