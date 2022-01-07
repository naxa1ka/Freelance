
package sample.DB;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Model.*;
import org.sqlite.JDBC;

import javax.jws.soap.SOAPBinding;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;


public class DbHandler {
    private static final String CON_STR = "jdbc:sqlite:src//sample//DB//fin.db";
    //connection in jar
    //private static final String CON_STR = "jdbc:sqlite::resource:myfin.db";
    private static DbHandler instance = null;

    private Connection connection = null;

    public static synchronized DbHandler getInstance() {
        if (instance == null)
            instance = new DbHandler();
        return instance;
    }

    private DbHandler() {
        try {
            DriverManager.registerDriver(new JDBC());
            connection = DriverManager.getConnection(CON_STR);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /************************************/

    public boolean isCan(int mask, Permission permission) {
       return (getMask(permission) & mask)> 0;
    }

    public int getMask(Permission permission) {
        try {
            String query = "SELECT MASK FROM PERMISSIONS WHERE DESCRIPTION = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, String.valueOf(permission));
            ResultSet resultSet = statement.executeQuery();
            return resultSet.getInt("MASK");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /*********************************************************/

    public boolean isExistUser(String login) {
        try {
            String query = "SELECT COUNT(*) FROM USERS WHERE LOGIN = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isExistFootballer(String name) {
        try {
            String query = "SELECT COUNT(*) FROM FOOTBALLERS WHERE NAME = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isExistSpecialization(String name) {
        try {
            String query = "SELECT COUNT(*) FROM SPECIALIZATIONS WHERE ROLE = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isExistCommand(String name) {
        try {
            String query = "SELECT COUNT(*) FROM COMMANDS WHERE NAME = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /*********************************************************/
    public List<Footballer> getAllFootballers() {
        try (Statement statement = connection.createStatement()) {
            List<Footballer> footballers = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT FOOTBALLERS.FOOTBALLER_ID,\n" +
                            "       FOOTBALLERS.NAME,\n" +
                            "       FOOTBALLERS.ALL_GAMES,\n" +
                            "       FOOTBALLERS.SUCCESS_GAMES,\n" +
                            "       SPECIALIZATIONS.ROLE,\n" +
                            "       FOOTBALLERS.SPECIALIZATION_ID,\n" +
                            "       FOOTBALLERS.COMMAND_ID,\n" +
                            "       COMMANDS.NAME as COMMANDS_NAME\n" +
                            "  FROM SPECIALIZATIONS\n" +
                            "       JOIN\n" +
                            "       FOOTBALLERS ON SPECIALIZATIONS.SPECIALIZATION_ID = FOOTBALLERS.SPECIALIZATION_ID\n" +
                            "       JOIN\n" +
                            "       COMMANDS ON COMMANDS.COMMAND_ID = FOOTBALLERS.COMMAND_ID;\n"
            );
            while (resultSet.next()) {
                footballers.add(new Footballer(
                        resultSet.getInt("FOOTBALLER_ID"),
                        resultSet.getString("NAME"),
                        new Specialization(
                                resultSet.getInt("SPECIALIZATION_ID"),
                                resultSet.getString("ROLE")
                        ),
                        resultSet.getInt("ALL_GAMES"),
                        resultSet.getInt("SUCCESS_GAMES"),
                        new Command(
                                resultSet.getInt("COMMAND_ID"),
                                resultSet.getString("COMMANDS_NAME")
                        )
                ));
            }
            return footballers;

        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public ObservableList<Footballer> getObservableListOfFootballers(){
        List<Footballer> rooms = getAllFootballers();
        return FXCollections.observableArrayList(rooms);
    }

    public void clearFootballers(){
        try {
            String query = "DELETE FROM FOOTBALLERS";
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUser(String login, String password) {
        User user = null;
        try {
            String query = "SELECT * FROM USERS WHERE LOGIN = ? AND PASSWORD = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String firstName = resultSet.getString("FIRST_NAME");
                int mask = resultSet.getInt("PERMISSION_MASK");
                user = new User(login, password, firstName, mask);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public Specialization getSpecialization(String name) {
        Specialization specialization = null;
        try {
            String query = "SELECT * FROM SPECIALIZATIONS WHERE ROLE = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String role = resultSet.getString("ROLE");
                int id = resultSet.getInt("SPECIALIZATION_ID");
                specialization = new Specialization(id, role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return specialization;
    }

    //TODO: problems with prepared statements
    public void updateFootballer(Footballer footballer) {
        try {
            String query = "UPDATE FOOTBALLERS SET NAME ='" + footballer.getName() +
                    "', ALL_GAMES =" + footballer.getAllGames() +
                    ", SUCCESS_GAMES =" + footballer.getSuccessGames() +
                    ", SPECIALIZATION_ID =" + footballer.getSpecialization().getId() +
                    " WHERE FOOTBALLER_ID =" + footballer.getId();
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCommand(Command command) {
        try {
            String query = "UPDATE COMMANDS SET NAME ='" + command.getName() +
                    "' WHERE COMMAND_ID =" + command.getId();
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSpecialization(Specialization specialization) {
        try {
            String query = "UPDATE SPECIALIZATIONS SET ROLE ='" + specialization.getRole() +
                    "' WHERE SPECIALIZATION_ID =" + specialization.getId();
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePermission(String login, int mask) {
        try {
            String query = "UPDATE USERS SET PERMISSION_MASK ='" + mask +
                    "' WHERE LOGIN ='" + login +"'";
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isExistMatch(LocalDate date) {
        try {
            String query = "SELECT COUNT(*) FROM MATCHES WHERE DATE = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, String.valueOf(date));
            ResultSet resultSet = statement.executeQuery();
            return resultSet.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getCommandName(int id) {
        try {
            String query = "SELECT NAME FROM COMMANDS WHERE COMMAND_ID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.getString("NAME");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public List<Match> getAllMatches(){
        try (Statement statement = connection.createStatement()) {
            List<Match> matches = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM MATCHES");
            while (resultSet.next()) {
                int fcid = resultSet.getInt("FIRST_COMMAND");
                int scid = resultSet.getInt("SECOND_COMMAND");
                matches.add(new Match(
                        new Command(fcid, getCommandName(fcid)),
                        new Command(scid, getCommandName(scid)),
                        LocalDate.parse(resultSet.getString("Date")))
                );
            }
            return matches;

        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public void addMatch(Match match){
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO MATCHES(`FIRST_COMMAND`, `SECOND_COMMAND`, `DATE`) " +
                        "VALUES(?, ?, ?)")) {
            statement.setObject(1, match.getFirstCommand().getId());
            statement.setObject(2, match.getSecondCommand().getId());
            statement.setObject(3, match.getDate());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addUser(User user) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO USERS(`FIRST_NAME`, `LOGIN`, `PASSWORD`) " +
                        "VALUES(?, ?, ?)")) {
            statement.setObject(1, user.getFirstName());
            statement.setObject(2, user.getLogin());
            statement.setObject(3, user.getPassword());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addFootballer(Footballer footballer) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO FOOTBALLERS(`NAME`, `ALL_GAMES`, `SUCCESS_GAMES`, `SPECIALIZATION_ID`, `COMMAND_ID`) " +
                        "VALUES(?, ?, ?, ?, ?)")) {
            statement.setObject(1, footballer.getName());
            statement.setObject(2, footballer.getAllGames());
            statement.setObject(3, footballer.getSuccessGames());
            statement.setObject(4, footballer.getSpecialization().getId());
            statement.setObject(5, footballer.getCommand().getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addSpecialization(Specialization specialization) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO SPECIALIZATIONS(`ROLE`) " +
                        "VALUES(?)")) {
            statement.setObject(1, specialization.getRole());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCommand(Command command) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO COMMANDS(`NAME`) " +
                        "VALUES(?)")) {
            statement.setObject(1, command.getName());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private User getUser(User user) {
        User newUser = user;
        try {
            String query = "SELECT * FROM USERS WHERE LOGIN = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getLogin());
            ResultSet resultSet = statement.executeQuery();
            newUser = new User(
                    resultSet.getString("LOGIN"),
                    resultSet.getString("PASSWORD"),
                    resultSet.getString("FIRST_NAME"),
                    resultSet.getInt("MASK")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newUser;
    }

    public List<Specialization> getAllSpecializations() {
        try (Statement statement = connection.createStatement()) {
            List<Specialization> specializations = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM SPECIALIZATIONS");
            while (resultSet.next()) {
                specializations.add(new Specialization(
                        resultSet.getInt("SPECIALIZATION_ID"),
                        resultSet.getString("ROLE")
                ));
            }
            return specializations;

        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<Command> getAllCommands() {
        try (Statement statement = connection.createStatement()) {
            List<Command> commands = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM COMMANDS");
            while (resultSet.next()) {
                commands.add(new Command(
                        resultSet.getInt("COMMAND_ID"),
                        resultSet.getString("NAME")
                ));
            }
            return commands;

        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    //TODO: problems with prepared statements
    public void deleteSpecialization(int specializationId) {
        try {
            String query = "DELETE FROM SPECIALIZATIONS WHERE SPECIALIZATION_ID = " + specializationId;
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //TODO: problems with prepared statements
    public void deleteFootballer(int footballerId) {
        try {
            String query = "DELETE FROM FOOTBALLERS WHERE FOOTBALLER_ID = " + footballerId;
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCommand(int commandId) {
        try {
            String query = "DELETE FROM COMMANDS WHERE COMMAND_ID = " + commandId;
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
