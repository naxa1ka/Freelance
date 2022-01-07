package sample.Controller;

import com.sun.javafx.scene.control.skin.DatePickerSkin;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import sample.DB.DbHandler;
import sample.Model.Match;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class Calendar extends BaseCalendar implements Initializable {

    @FXML
    private AnchorPane anchor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        allMatches = db.getAllMatches();

        DatePicker datePicker = new DatePicker(LocalDate.now());
        datePicker.setDayCellFactory(dayCellFactory);
        DatePickerSkin datePickerSkin = new DatePickerSkin(datePicker);
        Node popupContent = datePickerSkin.getPopupContent();
        anchor.getChildren().setAll(popupContent);

    }


}
