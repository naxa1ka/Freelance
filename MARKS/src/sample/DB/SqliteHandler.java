package sample.DB;

import com.sun.xml.internal.bind.v2.model.core.ID;
import org.sqlite.JDBC;
import sample.Model.Beans.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SqliteHandler {
    private static final String CON_STR = "jdbc:sqlite:src//sample//DB//fin.db";
    //connection in jar
    //private static final String CON_STR = "jdbc:sqlite::resource:myfin.db";
    private static SqliteHandler instance = null;

    private Connection connection = null;

    public static synchronized SqliteHandler getInstance() {
        if (instance == null)
            instance = new SqliteHandler();
        return instance;
    }

    private SqliteHandler() {
        try {
            DriverManager.registerDriver(new JDBC());
            connection = DriverManager.getConnection(CON_STR);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /************************************************************/

    public User getUser(String login, String password) {
        User user = null;
        try {
            String query = "SELECT * FROM USER WHERE LOGIN = ? AND PASSWORD = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("USER_ID");
                user = new User(id, login, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void addUser(User user) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO USER(`LOGIN`, `PASSWORD`) " +
                        "VALUES(?, ?)")) {
            statement.setObject(1, user.getLogin());
            statement.setObject(2, user.getPassword());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /************************************************************/

    public <T extends Bean> boolean isExist(T item) {
        if (item instanceof Country) {
            return isExistCountry((Country) item);
        } else if (item instanceof Serie) {
            return isExistSerie((Serie) item);
        } else if (item instanceof Collection) {
            return isExistCollection((Collection) item);
        } else if (item instanceof Mark) {
            return isExistMark((Mark) item);
        }
        return false;
    }

    public boolean isExist(User user) {
        String query = "SELECT COUNT(*) FROM USER WHERE LOGIN = ?";
        return isExistBase(query, user.getLogin());
    }

    public boolean isExistCountry(Country country) {
        String query = "SELECT COUNT(*) FROM COUNTRY WHERE NAME = ?";
        return isExistBase(query, country.getName());
    }

    public boolean isExistSerie(Serie serie) {
        String query = "SELECT COUNT(*) FROM SERIE WHERE NAME = ?";
        return isExistBase(query, serie.getName());
    }

    public boolean isExistCollection(Collection collection) {
        String query = "SELECT COUNT(*) FROM COLLECTION WHERE NAME = ?";
        return isExistBase(query, collection.getName());
    }

    public boolean isExistMark(Mark mark) {
        String query = "SELECT COUNT(*) FROM MARK WHERE SERIE_ID = ? AND COUNTRY_ID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, mark.getSerie().getId());
            statement.setInt(2, mark.getCountry().getId());
            ResultSet resultSet = statement.executeQuery();
            return resultSet.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isExist(Collection collection, Mark mark) {
        String query = "SELECT COUNT(*) FROM LIST_COLLECTION WHERE COLLECTION_ID = ? AND MARK_ID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, collection.getId());
            statement.setInt(2, mark.getId());
            ResultSet resultSet = statement.executeQuery();
            return resultSet.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isExistBase(String query, String name) {
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /************************************************************/

    public <T extends Bean> void add(T item) {
        if (item instanceof Country) {
            addCountry((Country) item);
        } else if (item instanceof Serie) {
            addSerie((Serie) item);
        } else if (item instanceof Collection) {
            addCollection((Collection) item);
        } else if (item instanceof Mark) {
            addMark((Mark) item);
        }
    }

    public void addCountry(Country country) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO COUNTRY(`NAME`) " +
                        "VALUES(?)")) {
            statement.setObject(1, country.getName());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addMark(Mark mark) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO MARK(`SERIE_ID`, `COUNTRY_ID`) " +
                        "VALUES(?, ?)")) {
            statement.setObject(1, mark.getSerie().getId());
            statement.setObject(2, mark.getCountry().getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addSerie(Serie serie) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO SERIE(`NAME`, `YEAR`) " +
                        "VALUES(?, ?)")) {
            statement.setObject(1, serie.getName());
            statement.setObject(2, serie.getYear());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCollection(Collection collection) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO COLLECTION(`NAME`) " +
                        "VALUES(?)")) {
            statement.setObject(1, collection.getName());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /************************************************************/

    public <T extends Bean> void delete(T item) {
        if (item instanceof Country) {
            deleteCountry((Country) item);
        } else if (item instanceof Serie) {
            deleteSerie((Serie) item);
        } else if (item instanceof Collection) {
            deleteCollection((Collection) item);
        } else if (item instanceof Mark) {
            deleteMark((Mark) item);
        }
    }

    public void deleteCountry(Country country) {
        String query = "DELETE FROM COUNTRY WHERE COUNTRY_ID = ";
        deleteBase(query, country.getId());
    }

    public void deleteSerie(Serie serie) {
        String query = "DELETE FROM SERIE WHERE SERIE_ID = ";
        deleteBase(query, serie.getId());
    }

    public void deleteCollection(Collection collection) {
        String query = "DELETE FROM COLLECTION WHERE COLLECTION_ID = ";
        deleteBase(query, collection.getId());
    }

    public void deleteMark(Mark mark) {
        String query = "DELETE FROM MARK WHERE MARK_ID = ";
        deleteBase(query, mark.getId());
    }

    private void deleteBase(String query, int id) {
        try {
            query += id;
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /************************************************************/

    public <T extends Bean> void updateCountry(T oldItem, T newItem) {
        if (oldItem instanceof Country) {
            updateCountry((Country) oldItem, (Country) newItem);
        } else if (oldItem instanceof Serie) {
            updateSerie((Serie) oldItem, (Serie) newItem);
        } else if (oldItem instanceof Collection) {
            updateCollection((Collection) oldItem, (Collection) newItem);
        } else if (oldItem instanceof Mark) {
            updateMark((Mark) oldItem, (Mark) newItem);
        }
    }

    public void updateCountry(Country oldCountry, Country newCountry) {
        try {
            String query = "UPDATE COUNTRY SET NAME ='" + newCountry.getName() +
                    "' WHERE COUNTRY_ID =" + oldCountry.getId();
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSerie(Serie oldSerie, Serie newSerie) {
        try {
            String query = "UPDATE SERIE SET NAME ='" + newSerie.getName() +
                    "', YEAR = '" + newSerie.getYear() + "' WHERE SERIE_ID =" + oldSerie.getId();
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCollection(Collection oldCollection, Collection newCollection) {
        try {
            String query = "UPDATE COLLECTION SET NAME ='" + newCollection.getName() +
                    "' WHERE COLLECTION_ID =" + oldCollection.getId();
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateMark(Mark oldMark, Mark newMark) {
        try {
            String query = "UPDATE SERIE SET SERIE_ID ='" + newMark.getSerie().getId() +
                    "', COUNTRY_ID = '" + newMark.getCountry().getId() + "' WHERE MARK_ID =" + oldMark.getId();
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateListCollection(ListCollection oldList, ListCollection newList) {
        try {
            String query = "UPDATE LIST_COLLECTION SET MARK_ID ='" + newList.getMark().getId() +
                    "' WHERE COLLECTION_ID =" + oldList.getId();
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /************************************************************/
    public List<Country> getAllCountry() {
        try (Statement statement = connection.createStatement()) {
            List<Country> countries = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM COUNTRY");
            while (resultSet.next()) {
                countries.add(new Country(
                        resultSet.getInt("COUNTRY_ID"),
                        resultSet.getString("NAME")
                ));
            }
            return countries;

        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public void deleteListCollection(Collection collection, Mark mark) {
        String query = "DELETE FROM LIST_COLLECTION WHERE MARK_ID = " + mark.getId() + " AND COLLECTION_ID = ";
        deleteBase(query, collection.getId());
    }

    public List<Serie> getAllSerie() {
        try (Statement statement = connection.createStatement()) {
            List<Serie> series = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM SERIE");
            while (resultSet.next()) {
                series.add(new Serie(
                        resultSet.getInt("SERIE_ID"),
                        resultSet.getString("NAME"),
                        resultSet.getInt("YEAR")
                ));
            }
            return series;

        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<Mark> getAllMark() {
        try (Statement statement = connection.createStatement()) {
            List<Mark> marks = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT COUNTRY.NAME AS COUNTRY_NAME,\n" +
                            "       COUNTRY.COUNTRY_ID,\n" +
                            "       SERIE.NAME AS SERIE_NAME,\n" +
                            "       SERIE.SERIE_ID,\n" +
                            "       SERIE.YEAR,\n" +
                            "       MARK.MARK_ID\n" +
                            "  FROM MARK\n" +
                            "       JOIN\n" +
                            "       COUNTRY ON MARK.COUNTRY_ID = COUNTRY.COUNTRY_ID\n" +
                            "       JOIN\n" +
                            "       SERIE ON MARK.SERIE_ID = SERIE.SERIE_ID;\n"
            );
            while (resultSet.next()) {
                marks.add(new Mark(
                        resultSet.getInt("MARK_ID"),
                        new Country(
                                resultSet.getInt("COUNTRY_ID"),
                                resultSet.getString("COUNTRY_NAME")
                        ),
                        new Serie(
                                resultSet.getInt("SERIE_ID"),
                                resultSet.getString("SERIE_NAME"),
                                resultSet.getInt("YEAR")
                        )
                ));
            }
            return marks;

        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public int countPages(Collection collection) {
        int counter = 1;
        String query = "SELECT COUNT(*) FROM LIST_COLLECTION WHERE COLLECTION_ID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, collection.getId());
            ResultSet resultSet = statement.executeQuery();
            counter = resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return counter / 5 + 1;
    }

    public void addListCollection(Collection collection, Mark mark) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO LIST_COLLECTION(`MARK_ID`, `COLLECTION_ID`, `PAGE`) " +
                        "VALUES(?,?,?)")) {
            statement.setObject(1, mark.getId());
            statement.setObject(2, collection.getId());
            statement.setObject(3, countPages(collection));
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ListCollection> getAllListCollection(int id, int serieID) {
        String addQuery = "";
        if (serieID != -1) {
            addQuery = " AND MARK.SERIE_ID = " + serieID;
        }
        try (Statement statement = connection.createStatement()) {
            List<ListCollection> collections = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT LIST_COLLECTION.MARK_ID,\n" +
                            "       LIST_COLLECTION.COLLECTION_ID,\n" +
                            "       LIST_COLLECTION.PAGE,\n" +
                            "       MARK.COUNTRY_ID,\n" +
                            "       MARK.SERIE_ID,\n" +
                            "       COUNTRY.NAME AS COUNTRY_NAME,\n" +
                            "       SERIE.NAME AS SERIE_NAME,\n" +
                            "       SERIE.YEAR\n" +
                            "  FROM LIST_COLLECTION\n" +
                            "       JOIN\n" +
                            "       MARK ON LIST_COLLECTION.MARK_ID = MARK.MARK_ID\n" +
                            "       JOIN\n" +
                            "       COUNTRY ON MARK.COUNTRY_ID = COUNTRY.COUNTRY_ID\n" +
                            "       JOIN\n" +
                            "       SERIE ON MARK.SERIE_ID = SERIE.SERIE_ID\n" +
                            " WHERE LIST_COLLECTION.COLLECTION_ID = " + id + addQuery
            );
            while (resultSet.next()) {
                collections.add(new ListCollection(
                        resultSet.getInt("COLLECTION_ID"),
                        resultSet.getInt("PAGE"),
                        new Mark(
                                resultSet.getInt("MARK_ID"),
                                new Country(
                                        resultSet.getInt("COUNTRY_ID"),
                                        resultSet.getString("COUNTRY_NAME")
                                ),
                                new Serie(
                                        resultSet.getInt("SERIE_ID"),
                                        resultSet.getString("SERIE_NAME"),
                                        resultSet.getInt("YEAR")
                                )
                        )
                ));
            }
            return collections;

        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<Collection> getAllCollection() {
        try (Statement statement = connection.createStatement()) {
            List<Collection> collections = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM COLLECTION");
            while (resultSet.next()) {
                collections.add(new Collection(
                        resultSet.getInt("COLLECTION_ID"),
                        resultSet.getString("NAME")
                ));
            }
            return collections;

        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<Country> getCountryListInCollection(Collection collection) {
        try (Statement statement = connection.createStatement()) {
            List<Country> collections = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT DISTINCT COUNTRY.NAME, COUNTRY.COUNTRY_ID FROM LIST_COLLECTION\n" +
                            "JOIN MARK\n" +
                            "ON LIST_COLLECTION.MARK_ID = MARK.MARK_ID\n" +
                            "JOIN COUNTRY\n" +
                            "ON MARK.COUNTRY_ID = COUNTRY.COUNTRY_ID\n" +
                            "WHERE COLLECTION_ID = " + collection.getId()
            );
            while (resultSet.next()) {
                collections.add(new Country(
                        resultSet.getInt("COUNTRY_ID"),
                        resultSet.getString("NAME")
                ));
            }
            return collections;

        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public void clearCollection(Collection collection){
        try {
            String query = "DELETE FROM LIST_COLLECTION WHERE COLLECTION_ID = " + collection.getId();
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
