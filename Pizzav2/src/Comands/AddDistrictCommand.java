package Comands;

import District.District;
import District.DistrictHandler;

public class AddDistrictCommand extends Command{
    @Override
    public String getCommandName() {
        return "adddist";
    }

    @Override
    public void execute() throws ExitException {

        System.out.println();

        System.out.println("Введите краткое название района");
        input();
        System.out.println("Введите полное название района");
        input();
    }
}
