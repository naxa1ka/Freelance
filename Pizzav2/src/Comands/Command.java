package Comands;

import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public abstract class Command {
    Scanner scanner;
    Queue<String> queue = new LinkedList<>();

    public abstract String getCommandName();

    public void input() throws ExitException {
        String input = scanner.nextLine();
        if (input.equals("end")) {
            queue.clear();
            throw new ExitException();
        }
        queue.add(input);
    }

    public abstract void execute() throws ExitException;

    public Queue<String> getQueue() {
        return queue;
    }
}
