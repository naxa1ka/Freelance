package sample.Controller.Adding;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import sample.Controller.BaseCalendar;
import sample.Controller.Initing;
import sample.DB.DbHandler;
import sample.Model.Command;
import sample.Model.Match;
import sample.Utils.Alerts;

import java.net.URL;
import java.time.LocalDate;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AddingMatch extends BaseCalendar implements Initializable {

    @FXML
    private ComboBox<Command> firstCommandComboBox;

    @FXML
    private ComboBox<Command> secondCommandComboBox;

    @FXML
    private DatePicker date;


    @FXML
    void onAddButton() {
        if (firstCommandComboBox.getValue().equals(
                secondCommandComboBox.getValue())) {
            Alerts.alertWarning("Ошибка!", "Команды должны быть разными!");
            return;
        }

        if (date.getValue() == null) {
            Alerts.alertWarning("Пусто!", "Заполните дату!!");
            return;
        }

        if (date.getValue().isBefore(LocalDate.now())) {
            Alerts.alertWarning("Дата не верна!", "Дата должна быть больше текушей!");
            return;
        }

        if (db.isExistMatch(date.getValue())) {
            Alerts.alertWarning("Занято!", "Матч на это число уже запланирован!");
            return;
        }

        db.addMatch(new Match(
                firstCommandComboBox.getValue(),
                secondCommandComboBox.getValue(),
                date.getValue())
        );
        Alerts.alertInfo("Успех!", "Успешно добавлено!");
        allMatches = db.getAllMatches();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Initing.initCommandsComboBox(firstCommandComboBox);
        Initing.initCommandsComboBox(secondCommandComboBox);
        date.setDayCellFactory(dayCellFactory);
        allMatches = db.getAllMatches();
    }
}
