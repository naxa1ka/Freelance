package Comands;

import Graph.Edge;

public class RemovePathCommand extends Command{
    @Override
    public String getCommandName() {
        return "removepath";
    }

    @Override
    public void execute() throws ExitException {
        System.out.println("Введите название первого района");
        input();
        System.out.println("Введите название второго района");
        input();

    }
}
