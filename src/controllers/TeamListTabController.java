package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import datamodel.Datasource;
import datamodel.Team;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

public class TeamListTabController {

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private ListView<Team> teamListView;

    @FXML
    private Label teamNameLabelD;

    @FXML
    private Label rosterNameLabelD;

    @FXML
    private Label startWeekLabelD;

    @FXML
    private TableView rosterTableView;

    @FXML
    private Button openTeam;

    @FXML
    private Button editTeam;

    @FXML
    private Button deleteTeam;

    private MainWindowController mainWindowController;

    private TeamListTabController parentWindowController; // is assigned through FXML, but intellij does not recognize


    public void initialize() {
        teamListView.setItems(Datasource.getInstance().getTeamData());

        // change RHS labels depending on selected
        teamListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Team>() {
            @Override
            public void changed(ObservableValue<? extends Team> observable, Team oldValue, Team newValue) {
                if (newValue != null) {
                    teamNameLabelD.textProperty().bind(newValue.teamNameProperty());
                    rosterNameLabelD.textProperty().bind(newValue.getRoster().nameProperty());
                    startWeekLabelD.textProperty().bind(newValue.startWeekProperty().asString());
                    // display roster depending on start week
                    ObservableList orderedWeeks = FXCollections.observableArrayList();
                    orderedWeeks.addAll(newValue.getRoster().getWeeks());
                    Collections.rotate(orderedWeeks, (-newValue.getStartWeek() + 1));
                    rosterTableView.setItems(orderedWeeks);
                }
            }
        });

        // disables buttons if no team is selected
        openTeam.disableProperty().bind(teamListView.getSelectionModel().selectedItemProperty().isNull());
        editTeam.disableProperty().bind(teamListView.getSelectionModel().selectedItemProperty().isNull());
        deleteTeam.disableProperty().bind(teamListView.getSelectionModel().selectedItemProperty().isNull());

    }

    // deletes selected team with confirmation
    @FXML
    public void deleteTeam() {
        Team selectedTeam = teamListView.getSelectionModel().getSelectedItem();
        if (selectedTeam != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deleting Team");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete team " + selectedTeam.getTeamName() +
                    "? This will also delete all employees associated with this team.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Datasource.getInstance().deleteTeam(selectedTeam);
                System.out.println("Deleting team");
            }
        }
    }

    // opens up the selected team tab
    @FXML
    public void openTeamTab() {
        Team selectedTeam = teamListView.getSelectionModel().getSelectedItem();
        if (selectedTeam != null) {
            mainWindowController.createTeamTab(selectedTeam);
        }
    }

    // opens the window to create new team
    @FXML
    public void newTeam() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/newTeamWindow.fxml"));
            Parent root = fxmlLoader.load();
            NewTeamController controller = fxmlLoader.getController();
            controller.setTeamListTabController(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initOwner(mainBorderPane.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setTitle("New Team");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setMainWindowController(MainWindowController controller) {
        this.mainWindowController = controller;
    }

    public void setParentWindowController(TeamListTabController controller) {
        this.parentWindowController = controller;
    }

    public void selectTeam(Team team) {
        teamListView.getSelectionModel().select(team);
    }

    // edits selected team and updates database
    @FXML
    public boolean editTeam() {
        Team team = teamListView.getSelectionModel().getSelectedItem();
        if (team == null) {
            return false;
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/newTeamWindow.fxml"));
            Parent root = fxmlLoader.load();
            NewTeamController controller = fxmlLoader.getController();
            controller.setTeamListTabController(this);
            controller.editTeam(team);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initOwner(mainBorderPane.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setTitle("Edit Team");
            stage.showAndWait();
            teamListView.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    // refreshes table
    public void refreshTable() {
        rosterTableView.refresh();
    }

}


