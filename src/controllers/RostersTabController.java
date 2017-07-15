package controllers;

import datamodel.Datasource;
import datamodel.DialogUtilities;
import datamodel.Roster;
import datamodel.Week;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.util.Optional;


public class RostersTabController {

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private ListView<Roster> rosterListView;

    @FXML
    private TableView<Week> rosterTableView;

    @FXML
    private Button editRosterButton;

    @FXML
    private Button deleteRosterButton;


    public void initialize() {
        rosterListView.setItems(Datasource.getInstance().getRosterData());


        // show data in table for selected roster
        rosterListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Roster>() {
            @Override
            public void changed(ObservableValue<? extends Roster> observable, Roster oldValue, Roster newValue) {
                if (newValue != null) {
                    Roster selectedRoster = rosterListView.getSelectionModel().getSelectedItem();
                    rosterTableView.setItems(selectedRoster.getWeeks());
                } else {
                    rosterTableView.setItems(null);
                }
            }
        });

        // disables buttons unless roster is selected
        editRosterButton.disableProperty().bind(rosterListView.getSelectionModel().selectedItemProperty().isNull());
        deleteRosterButton.disableProperty().bind(rosterListView.getSelectionModel().selectedItemProperty().isNull());
    }

    // opens dialog to create new roster
    @FXML
    public void showNewRosterDialog() {
        Roster newRoster = DialogUtilities.showRosterDialog(mainBorderPane);
        if (newRoster != null) {
            Datasource.getInstance().insertRosterFull(newRoster);
            rosterListView.getSelectionModel().select(newRoster);
        }
    }

    // deletes roster with confirmation
    @FXML
    public void deleteRoster() {
        Roster roster = rosterListView.getSelectionModel().getSelectedItem();
        if (roster == null) {
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Roster");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete " + roster.getName() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Datasource.getInstance().deleteRoster(roster);
            rosterListView.refresh();
        }

    }

    // opens the edit roster dialog & updates database
    @FXML
    public void editRoster() {
        Roster roster = rosterListView.getSelectionModel().getSelectedItem();
        if (roster == null) {
            return;
        }
        Roster updatedRoster = DialogUtilities.showEditRosterDialog(mainBorderPane, roster);
        if (updatedRoster == null) {
            return;
        }
        if (Datasource.getInstance().updateRoster(roster, updatedRoster.getName(), updatedRoster.getWeeks())) {
            roster.setName(updatedRoster.getName());
            roster.setWeeks(updatedRoster.getWeeks());
            rosterListView.refresh();
            rosterTableView.refresh();
        }

    }

}




