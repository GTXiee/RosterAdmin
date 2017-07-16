package controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import datamodel.Roster;
import datamodel.Week;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;

public class NewRosterController {

    @FXML
    private DialogPane dialogPane;

    @FXML
    private TextField rosterNameTextField;

    @FXML
    private TableView<Week> rosterTableView;

    @FXML
    private TextField weekNumberTextField;

    @FXML
    private TextField rosteredTextField;

    @FXML
    private TextField saturdaysTextField;

    @FXML
    private TextField sundaysTextField;

    @FXML
    private Button addWeekButton;

    @FXML
    private ContextMenu listContextMenu;

    @FXML
    private Label weekGapLabel;

    // error types
    public static final int INVALID_START_WEEK = 1;
    public static final int POSITIVE_NUMERIC_VALUE = 2;

    ObservableList<Week> weekObservableList = FXCollections.observableArrayList();
    SortedList<Week> sortedWeeks = new SortedList<Week>(weekObservableList, new Comparator<Week>() {
        @Override
        public int compare(Week o1, Week o2) {
            return o1.getNumber() - o2.getNumber();
        }
    });

    HashSet<Integer> existingWeeks = new HashSet<>();


    public void initialize() {

        // right click to delete week
        listContextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Week week = rosterTableView.getSelectionModel().getSelectedItem();
                deleteWeek(week);
            }
        });

        listContextMenu.getItems().addAll(deleteMenuItem);
        rosterTableView.setRowFactory(new Callback<TableView<Week>, TableRow<Week>>() {
            @Override
            public TableRow<Week> call(TableView<Week> param) {
                TableRow<Week> row = new TableRow<>();

                // only display context menu for non-null items:
                row.contextMenuProperty().bind(
                        Bindings.when(Bindings.isNotNull(row.itemProperty()))
                                .then(listContextMenu)
                                .otherwise((ContextMenu) null));
                return row;
            }
        });


        rosterTableView.setItems(sortedWeeks);

        // pre loads week number textfield with next week
        IntegerBinding sizeProperty = Bindings.size(weekObservableList).add(1);
        weekNumberTextField.textProperty().bind(sizeProperty.asString());

        // disables 'add week' button if fields are empty
        addWeekButton.disableProperty().bind(
                Bindings.isEmpty(weekNumberTextField.textProperty())
                        .or(Bindings.isEmpty(rosteredTextField.textProperty()))
                        .or(Bindings.isEmpty(saturdaysTextField.textProperty()))
                        .or(Bindings.isEmpty(sundaysTextField.textProperty()))
        );


    }

    // adds week to tableview using the fields
    public boolean handleAddWeek() {
        try {
            int weekNumber = Integer.parseInt(weekNumberTextField.getText());
            int rostered = Integer.parseInt(rosteredTextField.getText());
            int saturdays = Integer.parseInt(saturdaysTextField.getText());
            int sundays = Integer.parseInt(sundaysTextField.getText());

            // checks that fields are positive & week number hasn't already been added
            if (weekNumber > 0 && rostered >= 0 && saturdays >= 0
                    && sundays >= 0 && !existingWeeks.contains(weekNumber)) {
                weekObservableList.add(new Week(weekNumber, rostered, saturdays, sundays));
                existingWeeks.add(weekNumber);

                rosteredTextField.clear();
                saturdaysTextField.clear();
                sundaysTextField.clear();

                isWeekGap();
                return true;
            } else if (weekNumber > 0 || rostered >= 0 || saturdays >= 0 || sundays >= 0) {
                inputErrorMessage(POSITIVE_NUMERIC_VALUE);
            } else if (existingWeeks.contains(weekNumber)) {
                inputErrorMessage(INVALID_START_WEEK);
            }

        } catch (NumberFormatException e) {
            inputErrorMessage(POSITIVE_NUMERIC_VALUE);
        }
        return false;
    }

    // displays error message depending on constant
    public void inputErrorMessage(int alertConstant) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (alertConstant == INVALID_START_WEEK) {
            alert.setTitle("Invalid Start Week");
            alert.setHeaderText(null);
            alert.setContentText("Start week already exists. Please enter a different number.");
        } else if (alertConstant == POSITIVE_NUMERIC_VALUE) {
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter positive numeric values");
        }
        alert.showAndWait();
    }

    // deletes week
    public void deleteWeek(Week week) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Week");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete Week " + week.getNumber() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && (result.get() == ButtonType.OK)) {
            weekObservableList.remove(week);
            existingWeeks.remove(week.getNumber());
            isWeekGap();
        }
    }

    // check for a gap between week number and warns user if exists
    public boolean isWeekGap() {
        int i = 1;
        if (weekObservableList.isEmpty()) {
            return true;
        }
        for (Week week : sortedWeeks) {
            if (week.getNumber() != i) {
                weekGapLabel.setText("Week " + i + " is missing");
                weekGapLabel.setTextFill(Color.valueOf("red"));
                System.out.println(dialogPane.getParent());
                return true;
            }
            i++;
        }
        weekGapLabel.setText("");
        return false;
    }

    // check if a valid roster name exists
    public boolean isRosterName() {
        if (rosterNameTextField.getText().trim().equals("")) {
            return false;
        }
        return true;
    }

    // creates and returns a new roster from the fields
    public Roster newRoster() {
        String rosterName = rosterNameTextField.getText();
        Roster newRoster = new Roster(rosterName, weekObservableList);
        return newRoster;
    }

    // sets the fields so roster is ready to be edited
    public void editRoster(Roster roster) {
        rosterNameTextField.setText(roster.getName());
        this.weekObservableList.addAll(roster.getWeeks());
        for (Week week : weekObservableList) {
            existingWeeks.add(week.getNumber());
        }
    }


}
