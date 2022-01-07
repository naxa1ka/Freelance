package Comands;

public class ExitException extends Exception {
    ExitException() {
        super("Данные не будут сохранены!");
    }
}
