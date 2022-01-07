package sample.Loader;


import static sample.Loader.SceneView.Way.*;

public enum SceneView {

    ENTER(way + auth + "enter.fxml", "Авторизация"),
    REGISTER(way + auth + "register.fxml", "Регистрация"),

    ADD_COUNTRY(way + add + "country.fxml", "Добавление страны"),
    ADD_SERIE(way + add + "serie.fxml", "Добавление серии"),
    ADD_MARK(way + add + "mark.fxml", "Добавление марки"),
    ADD_COLLECTION(way + add + "collection.fxml", "Добавление коллекции"),

    EDIT_COUNTRY(way + edit + "country.fxml", "Изменение страны"),
    EDIT_SERIE(way + edit + "serie.fxml", "Изменение серии"),
    EDIT_MARK(way + edit + "mark.fxml", "Изменение марки"),
    EDIT_COLLECTION(way + edit + "collection.fxml", "Изменение коллекции"),

    DELETE_COUNTRY(way + delete + "country.fxml", "Удаление страны"),
    DELETE_SERIE(way + delete + "serie.fxml", "Удаление серии"),
    DELETE_MARK(way + delete + "mark.fxml", "Удаление марки"),
    DELETE_COLLECTION(way + delete + "collection.fxml", "Удаление коллекции"),

    LIST_COUNTRY(way + "listCountry.fxml", "Страны"),

    MAIN(way + "main.fxml", "Главное меню");

    static class Way {
        static final String way = "View/";
        static final String auth = "Auth/";
        static final String add = "Add/";
        static final String edit = "Edit/";
        static final String delete = "Delete/";
    }

    private final String path;
    private final String title;

    SceneView(String path, String title) {
        this.path = path;
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public String getTitle() {
        return title;
    }


}