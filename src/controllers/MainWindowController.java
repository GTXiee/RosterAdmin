package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import datamodel.Team;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainWindowController {

    @FXML
    private TabPane mainTabPane;

    @FXML
    private Tab teamTab;

    @FXML
    private WelcomeTabController welcomeTabPageController; // is assigned through FXML, but intellij does not recognize

    @FXML
    private TeamListTabController teamTabPageController; // is assigned through FXML, but intellij does not recognize

    private Map<Integer, Tab> openTabs = new HashMap<Integer, Tab>();


    public void initialize() {
        welcomeTabPageController.setMainWindowController(this);
        teamTabPageController.setMainWindowController(this);

        // refreshes the tableview when team tab is selected
        mainTabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                if (newValue.equals(teamTab)) {
                    teamTabPageController.refreshTable();
                }
            }
        });

    }

    // creates team tab, provided one is not already open
    public void createTeamTab(Team team) {
        if (openTabs.keySet().contains(team.getId())) {
            System.out.println("Tab already open");
            return;
        }
        Tab newTab = new Tab(team.getTeamName());
        newTab.setOnClosed(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                openTabs.remove(team.getId());
            }
        });
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/teamTab.fxml"));
            Parent root = fxmlLoader.load();
            TeamTabController controller = fxmlLoader.getController();
            controller.setTeam(team);
            newTab.setContent(root);
            controller.init();
            openTabs.put(team.getId(), newTab);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mainTabPane.getTabs().add(newTab);
        mainTabPane.getSelectionModel().select(newTab);

    }

    // display new window with manual
    @FXML
    public void showManual() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/manual.fxml"));
        try {
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Manual");
            stage.show();
        } catch (IOException e) {
            System.out.println("Could not load manual.fxml: " + e.getMessage());
        }
    }

}
