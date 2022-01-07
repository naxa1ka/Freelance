import Comands.*;
import District.District;
import District.DistrictHandler;
import Graph.*;
import People.Admin;
import People.Client;
import People.User;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    static List<User> users = new ArrayList<>();
    static City<District> city = new City<>();
    static CommandsHandler commandsHandler = new CommandsHandler();
    static Scanner scanner = new Scanner(System.in);
    static Queue<String> queue = new LinkedList<>();
    static boolean isFile = false;

    public static void main(String[] args) throws IOException {
        users.add(new Admin("root", "root"));
        createCity();

        if (true){
            fromFile("result_20_05_2021_03_16_33");
        }

        mainMenu();
    }

    private static void fromFile(String fileName) throws IOException {
        isFile = true;
        Path path = Paths.get(fileName);
        scanner = new Scanner(path);
        commandsHandler = new CommandsHandler(scanner);
        mainMenu();
        scanner.close();

        scanner = new Scanner(System.in);
        commandsHandler = new CommandsHandler();
        System.out.println("----------");
        isFile = false;
    }

    private static void mainMenu() throws IOException {
        String choose = "";
        while (!choose.equals("end")) {
            if (isFile && !scanner.hasNext()) return;
            System.out.println("[Reg/r] Регистрация пользователя");
            System.out.println("[Enter/ent] Вход");
            System.out.println("[end] Выход");
            choose = scanner.nextLine().toLowerCase();
            switch (choose) {
                case "reg":
                case "r":
                    DistrictHandler.getOptions();
                    commandsHandler.setCurrentCommand(new RegisterCommand());
                    commandsHandler.execute();
                    queue = commandsHandler.getQueue();
                    users.add(new Client(
                            queue.poll(),
                            queue.poll(),
                            DistrictHandler.getDistrict(queue.poll())
                    ));
                    System.out.println("Register client!");
                    break;
                case "enter":
                case "ent":
                    commandsHandler.setCurrentCommand(new EnterCommand());
                    commandsHandler.execute();
                    queue = commandsHandler.getQueue();
                    User currentUser = findUser(new User(queue.poll(), queue.poll()));

                    if (currentUser == null) {
                        System.out.println("Такого пользователя не существует");
                    } else if (currentUser instanceof Admin) {
                        adminMenu();
                    } else if (currentUser instanceof Client) {
                        clientMenu((Client) currentUser);
                    }
                    break;
                case "end":
                    System.out.println("Вы хотите сохраните результаты? YES | NO");
                    if (scanner.nextLine().equalsIgnoreCase("yes")) {
                        commandsHandler.writeCommands();
                    }
                    break;
                default:
                    System.out.println("Повторите ввод!");
                    break;
            }
        }
    }

    private static void clientMenu(Client client) {
        String choose = "";
        while (!choose.equals("end")) {
            if (isFile && !scanner.hasNext()) return;
            System.out.println("[Order/o] Заказать пиццу");
            System.out.println("[End] Выход");
            choose = scanner.nextLine().toLowerCase();
            switch (choose) {
                case "order":
                case "o":
                    System.out.println(client.getDistrict());
                    city.getShortestPath(
                            new Vertex<>(client.getDistrict())
                    );
                    break;
                case "end":
                    commandsHandler.addEnd();
                    break;
                default:
                    System.out.println("Повторите ввод!");
                    break;
            }
        }
    }

    private static void adminMenu() throws IOException {
        String choose = "";
        District district;
        while (!choose.equals("end")) {
            if (isFile && !scanner.hasNext()) return;
            System.out.println("[AddPath/ap] Добавить путь");
            System.out.println("[RemovePath/rp] Удалить путь");

            System.out.println("[AddCour/ac] Добавить курьера");
            System.out.println("[RemoveCour/rc] Удалить курьера");

            System.out.println("[AddDist/ad] Добавить район");
            System.out.println("[RemoveDist/rd] Удалить район");

            System.out.println("[End] Выход");
            choose = scanner.nextLine().toLowerCase();
            switch (choose) {
                case "addpath":
                case "ap":
                    DistrictHandler.getOptions();
                    commandsHandler.setCurrentCommand(new AddPathCommand());
                    commandsHandler.execute();
                    queue = commandsHandler.getQueue();
                    city.getGraph().addEdge(
                            new Edge<District>(
                                    new Vertex<>(DistrictHandler.getDistrict(queue.poll())),
                                    new Vertex<>(DistrictHandler.getDistrict(queue.poll())),
                                    Integer.parseInt(Objects.requireNonNull(queue.poll()))
                            )
                    );
                    break;
                case "removepath":
                case "rp":
                    commandsHandler.setCurrentCommand(new RemoveDistrictCommand());
                    commandsHandler.execute();
                    queue = commandsHandler.getQueue();
                    city.getGraph().removeEdge(
                            new Edge<District>(
                                    new Vertex<>(DistrictHandler.getDistrict(queue.poll())),
                                    new Vertex<>(DistrictHandler.getDistrict(queue.poll())),
                                    0
                            )
                    );
                    break;
                case "addcour":
                case "ac":
                    DistrictHandler.getOptions();
                    commandsHandler.setCurrentCommand(new AddCourierCommand());
                    commandsHandler.execute();
                    queue = commandsHandler.getQueue();
                    district = new District(queue.poll());
                    city.addCourier(new Vertex<>(district));
                    break;
                case "removecour":
                case "rc":
                    DistrictHandler.getOptions();
                    commandsHandler.setCurrentCommand(new RemoveCourierCommand());
                    commandsHandler.execute();
                    queue = commandsHandler.getQueue();
                    district = new District(queue.poll());
                    city.removeCourier(new Vertex<>(district));
                    break;
                case "adddist":
                case "ad":
                    DistrictHandler.getOptions();
                    commandsHandler.setCurrentCommand(new AddDistrictCommand());
                    commandsHandler.execute();
                    queue = commandsHandler.getQueue();
                    district = new District(queue.poll());
                    DistrictHandler.addDistrict(district);
                    city.getGraph().addVertex(new Vertex<>(district));
                    break;
                case "removedist":
                case "rd":
                    DistrictHandler.getOptions();
                    commandsHandler.setCurrentCommand(new RemoveDistrictCommand());
                    commandsHandler.execute();
                    queue = commandsHandler.getQueue();
                    district = new District(queue.poll());
                    DistrictHandler.removeDistrict(district);
                    city.getGraph().removeVertex(new Vertex<>(district));
                    break;
                case "end":
                    commandsHandler.addEnd();
                    break;
                default:
                    System.out.println("Повторите ввод!");
                    break;
            }
        }
    }

    /******************************************/

    private static User findUser(User inputUser) {
        for (User user : users) {
            if (user.enter(inputUser)) {
                return user;
            }
        }
        return null;
    }

    /***********************************/

    private static void createCity() {
        createDistricts();
        createVertex();
        createEdges();
        createCouriers();
    }

    private static void createDistricts() {
        DistrictHandler.addDistrict("CAO", "Центральный административный округ");
        DistrictHandler.addDistrict("NAO", "Cеверный административный округ");
        DistrictHandler.addDistrict("NEAO", "Северо восточный административный округ");
        DistrictHandler.addDistrict("EAO", "Восточный административный округ");
        DistrictHandler.addDistrict("SEAO", "Юго восточный административный округ");
        DistrictHandler.addDistrict("SAO", "Южный административный округ");
        DistrictHandler.addDistrict("SWAO", "Юго западный административный округ");
        DistrictHandler.addDistrict("WAO", "Западный административный округ");
        DistrictHandler.addDistrict("NWAO", "Северо западный административный округ");
        DistrictHandler.addDistrict("ZGAO", "Зеленоградский административный округ");
        DistrictHandler.addDistrict("NMAO", "Новомосковский административный округ"); //такого не существует :)
        DistrictHandler.addDistrict("MAO", "Московский административный округ");
        DistrictHandler.addDistrict("TRAO", "Троицкий административный округ");
        DistrictHandler.addDistrict("NTRAO", "Новотроицкий административный округ"); //такого не существует :)
    }

    private static void createVertex() {
        for (int i = 0; i < 14; i++) {
            city.getGraph().addVertex(new Vertex<>(DistrictHandler.getDistrict(i)));
        }
    }

    private static void createEdges() {
        int[][] graph = {
                {0, 2, 0, 1, 3, 4, 5, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 3, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 4, 0, 0, 0, 4, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 5, 0, 0, 0, 7, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 2, 0, 0, 5, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 10, 0, 0, 7, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 0, 3, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 4, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        city.getGraph().fromMatrixToGraph(graph);
    }

    private static void createCouriers() {
        city.addCourier(new Vertex<>(DistrictHandler.getDistrict(0)));
        city.addCourier(new Vertex<>(DistrictHandler.getDistrict(8)));
    }
}
