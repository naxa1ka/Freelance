package sample.Controller;

import javafx.scene.control.ComboBox;
import sample.DB.DbHandler;
import sample.Model.Command;
import sample.Model.Specialization;

import java.util.ArrayList;
import java.util.List;

public class Initing {
    private static DbHandler db = DbHandler.getInstance();
    public static void initSpecializationsComboBox(ComboBox<Specialization> comboBox) {
        List<Specialization> specializations = new ArrayList<>(db.getAllSpecializations());
        comboBox.getItems().addAll(specializations);
        comboBox.setValue(specializations.get(0));
    }

    public static void initCommandsComboBox(ComboBox<Command> comboBox) {
        List<Command> commands = new ArrayList<>(db.getAllCommands());
        comboBox.getItems().addAll(commands);
        comboBox.setValue(commands.get(0));
    }
}
