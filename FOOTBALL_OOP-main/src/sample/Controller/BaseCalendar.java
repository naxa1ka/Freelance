package sample.Controller;

import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tooltip;
import javafx.util.Callback;
import sample.DB.DbHandler;
import sample.Model.Match;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BaseCalendar {

    protected DbHandler db = DbHandler.getInstance();

    protected List<Match> allMatches = new ArrayList<>();

    protected final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
        public DateCell call(final DatePicker datePicker) {
            return new DateCell() {
                @Override
                public void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    for (Match m :
                            allMatches) {

                        if (item.equals(m.getDate())) {
                            setTooltip(new Tooltip(
                                    m.getFirstCommand().getName() + " vs " + m.getSecondCommand().getName()
                            ));
                            setStyle("-fx-background-color: #ff4444;");
                        }
                    }
                }
            };
        }
    };
}
