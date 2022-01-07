package Comands;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class CommandsHandler {
    Queue<String> commands = new LinkedList<>();
    Command currentCommand;
    Scanner scanner;

    public CommandsHandler(){
        scanner = new Scanner(System.in);
    }

    public CommandsHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    public void execute() {
        try {
            currentCommand.execute();
            commands.add(currentCommand.getCommandName());
        } catch (ExitException e) {
            System.out.println("Данные не будут сохранены!");
        }

        Queue<String> queue = currentCommand.getQueue();
        commands.addAll(queue);
    }

    public Queue<String> getQueue() {
        return currentCommand.getQueue();
    }

    public void setCurrentCommand(Command currentCommand) {
        this.currentCommand = currentCommand;
        currentCommand.scanner = scanner;
    }

    public void addEnd() {
        commands.add("end");
    }


    public void writeCommands() {
        DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
        Date date = new Date();
        try (FileWriter writer = new FileWriter("result_" + dateFormat.format(date), false)) {
            while (!commands.isEmpty()) {
                writer.write(commands.poll() + "\n");
            }
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
