package Comands;

import District.District;
import District.DistrictHandler;
import Graph.Edge;

import java.util.Objects;

public class AddPathCommand extends Command{
    @Override
    public String getCommandName() {
        return "ap";
    }

    @Override
    public void execute() throws ExitException {

        System.out.println();
        System.out.println("Введите название первого района");
        input();
        System.out.println("Введите название второго района");
        input();
        System.out.println("Введите вес пути");
        input();
    }
}
