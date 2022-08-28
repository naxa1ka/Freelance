package com.company;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String WORDS_FILE_NAME = "files/russian.txt";
    private static Scanner scanner;


    public static void main(String[] args) {
        test();

        scanner = new Scanner(System.in);

        app();
    }

    private static void test() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("10\n");
        stringBuilder.append("2\n");
        stringBuilder.append("Илья\n");
        stringBuilder.append("Михаил\n");

        stringBuilder.append("1\n");
        stringBuilder.append("т\n");
        stringBuilder.append("2\n");
        stringBuilder.append("2\n");

        stringBuilder.append("1\n");
        stringBuilder.append("ы\n");
        stringBuilder.append("2\n");
        stringBuilder.append("1\n");

        stringBuilder.append("1\n");
        stringBuilder.append("ы\n");
        stringBuilder.append("3\n");
        stringBuilder.append("2\n");

        stringBuilder.append("1\n");
        stringBuilder.append("к\n");
        stringBuilder.append("4\n");
        stringBuilder.append("2\n");

        stringBuilder.append("1\n");
        stringBuilder.append("в\n");
        stringBuilder.append("5\n");
        stringBuilder.append("2\n");

        stringBuilder.append("1\n");
        stringBuilder.append("а\n");
        stringBuilder.append("6\n");
        stringBuilder.append("2\n");

        stringBuilder.append("1\n");
        stringBuilder.append("о\n");
        stringBuilder.append("5\n");
        stringBuilder.append("3\n");

        stringBuilder.append("1\n");
        stringBuilder.append("й\n");
        stringBuilder.append("5\n");
        stringBuilder.append("4\n");

        stringBuilder.append("1\n");
        stringBuilder.append("н\n");
        stringBuilder.append("5\n");
        stringBuilder.append("5\n");

        stringBuilder.append("1\n");
        stringBuilder.append("а\n");
        stringBuilder.append("5\n");
        stringBuilder.append("6\n");

        stringBuilder.append("1\n");
        stringBuilder.append("б\n");
        stringBuilder.append("6\n");
        stringBuilder.append("3\n");

        stringBuilder.append("1\n");
        stringBuilder.append("а\n");
        stringBuilder.append("6\n");
        stringBuilder.append("4\n");

        stringBuilder.append("1\n");
        stringBuilder.append("ж\n");
        stringBuilder.append("6\n");
        stringBuilder.append("5\n");

        stringBuilder.append("1\n");
        stringBuilder.append("у\n");
        stringBuilder.append("6\n");
        stringBuilder.append("6\n");

        stringBuilder.append("1\n");
        stringBuilder.append("р\n");
        stringBuilder.append("6\n");
        stringBuilder.append("7\n");

        stringBuilder.append("1\n");
        stringBuilder.append("и\n");
        stringBuilder.append("7\n");
        stringBuilder.append("5\n");

        stringBuilder.append("1\n");
        stringBuilder.append("р\n");
        stringBuilder.append("8\n");
        stringBuilder.append("5\n");

        stringBuilder.append("1\n");
        stringBuilder.append("о\n");
        stringBuilder.append("4\n");
        stringBuilder.append("5\n");

        stringBuilder.append("1\n");
        stringBuilder.append("с\n");
        stringBuilder.append("3\n");
        stringBuilder.append("5\n");

        stringBuilder.append("1\n");
        stringBuilder.append("ы\n");
        stringBuilder.append("8\n");
        stringBuilder.append("8\n");

        stringBuilder.append("2\n");

        InputStream in = new ByteArrayInputStream(stringBuilder.toString().getBytes());
        System.setIn(in);
    }

    private static void app() {
        Game game = initGame();
        List<Player> players = initPlayers();

        String input;
        boolean isInputCorrect;

        while (true) {
            for (Player player : players) {
                do {
                    showVariants(player);

                    input = scanner.next();

                    if (isEndGame(players, input)) {
                        return;
                    }

                    isInputCorrect = isInputCorrect(game, input, player);

                    if (!isInputCorrect) {
                        System.out.println();
                        System.out.println("Что-то пошло не так...");
                        System.out.println("Повторите ввод!");
                    }

                } while (!isInputCorrect);
            }
        }

    }

    private static void showVariants(Player player) {
        System.out.println("");
        System.out.println(player.getName() + ", Ваш ход!");
        System.out.println("1. Ввести букву \n2. Выйти\n");
    }

    private static boolean isEndGame(List<Player> players, String input) {
        if (input.equals("2")) {
            System.out.println("Спасибо за игру!");
            showScore(players);
            return true;
        }

        return false;
    }

    private static boolean isInputCorrect(Game game, String input, Player player) {
        if (input.equals("1")) {
            System.out.println("Введите символ");
            char letter = scanner.next().charAt(0);

            System.out.println("Введите номер строки");
            int x = scanner.next().charAt(0) - '0';

            System.out.println("Введите номер столбца");
            int y = scanner.next().charAt(0) - '0';

            return game.tryAddLetter(x, y, letter, player);
        }

        return false;
    }

    private static Game initGame() {
        System.out.println("Введите размер сетки!");
        int size = Integer.parseInt(scanner.next());
        return new Game(size, WORDS_FILE_NAME);
    }

    private static List<Player> initPlayers() {
        System.out.println("Введите количество игроков!");
        int playerSize = Integer.parseInt(scanner.next());

        List<Player> players = new ArrayList<>();
        for (int i = 0; i < playerSize; i++) {
            System.out.println("Введите имя игрока!");
            String name = scanner.next();
            players.add(new Player(name));
        }

        return players;
    }

    private static void showScore(List<Player> players) {
        for (Player player : players) {
            System.out.println("У " + player.getName() + " " + player.getScore() + " очков!");
        }
    }
}

