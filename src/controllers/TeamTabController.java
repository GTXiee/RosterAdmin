package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import datamodel.Datasource;
import datamodel.Employee;
import datamodel.Team;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Predicate;

public class TeamTabController {
    private Team tabTeam;

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private ListView<Employee> employeeListView;

    @FXML
    private Label titleLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label fullTimeLabel;

    @FXML
    private Label rosteredHoursLabel;

    @FXML
    private Label saturdayHoursLabel;

    @FXML
    private Label sundayHoursLabel;

    @FXML
    private Label phHoursLabel;

    @FXML
    private Label phHoursCreditLabel;

    @FXML
    private Label shiftChangesLabel;

    @FXML
    private Button saveEmployeeButton;

    @FXML
    private Button editEmployeeButton;

    @FXML
    private Button deleteEmployeeButton;

    @FXML
    private Label savedLabel;

    // created own init() method here rather than use intialize() so it can be executed after the setTeam() method
    public void init() {
        titleLabel.setText(tabTeam.getTeamName());

        // filters listview to only show employees from the team
        employeeListView.setItems(Datasource.getInstance().getEmployeeData().filtered(new Predicate<Employee>() {
            @Override
            public boolean test(Employee employee) {
                return employee.getTeam().equals(tabTeam);
            }
        }));

        // binds all fields to the employee's details
        employeeListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Employee>() {
            @Override
            public void changed(ObservableValue<? extends Employee> observable, Employee oldValue, Employee newValue) {
                if (newValue != null) {
                    nameLabel.textProperty().bind(employeeListView.getSelectionModel().getSelectedItem().nameProperty());
                    fullTimeLabel.textProperty().bind(employeeListView.getSelectionModel().getSelectedItem().isFullTimeProperty().asString());
                    rosteredHoursLabel.textProperty().bind(employeeListView.getSelectionModel().getSelectedItem().totalHoursProperty().asString());
                    saturdayHoursLabel.textProperty().bind(employeeListView.getSelectionModel().getSelectedItem().saturdayHoursProperty().asString());
                    sundayHoursLabel.textProperty().bind(employeeListView.getSelectionModel().getSelectedItem().sundayHoursProperty().asString());
                    phHoursLabel.textProperty().bind(employeeListView.getSelectionModel().getSelectedItem().phHoursProperty().asString());
                    phHoursCreditLabel.textProperty().bind(employeeListView.getSelectionModel().getSelectedItem().phCreditProperty().asString());
                    shiftChangesLabel.textProperty().bind(employeeListView.getSelectionModel().getSelectedItem().shiftChangesProperty().asString());


                }
            }
        });

        // disables buttons if no employee is selected
        saveEmployeeButton.disableProperty().bind(employeeListView.getSelectionModel().selectedItemProperty().isNull());
        editEmployeeButton.disableProperty().bind(employeeListView.getSelectionModel().selectedItemProperty().isNull());
        deleteEmployeeButton.disableProperty().bind(employeeListView.getSelectionModel().selectedItemProperty().isNull());
    }

    // shows window to create a new team employee
    public void showNewTeamEmployees() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/teamTargets.fxml"));
        try {
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Employee Targets");
            stage.initOwner(mainBorderPane.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.resizableProperty().set(false);
            TeamEmployeesController controller = fxmlLoader.getController();
            controller.setTeam(tabTeam);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // sets the team property
    public void setTeam(Team team) {
        if (team != null) {
            this.tabTeam = team;
        }
    }

    // deletes employee with confirmation
    @FXML
    public boolean deleteEmployee() {
        Employee employee = employeeListView.getSelectionModel().getSelectedItem();
        if (employee == null) {
            return false;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deleting Employee");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete " + employee.getName() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            return Datasource.getInstance().deleteEmployee(employee);
        }
        return false;
    }

    // saves the selected employee details as an XML file. message appears depending if save was successful
    @FXML
    public void saveEmployee() {
        Employee employee = employeeListView.getSelectionModel().getSelectedItem();
        if (employee != null) {
            try {
                if (Employee.saveEmployee(employee, mainBorderPane)) {
                    savedLabel.setVisible(true);
                }
            } catch (Exception e) {
                savedLabel.setText("Error while saving file");
                savedLabel.setTextFill(Color.RED);
                savedLabel.setVisible(true);
            }
        }
    }

    // edits the selected employee and updates the database
    @FXML
    public void editEmployee() {
        Employee employee = employeeListView.getSelectionModel().getSelectedItem();
        if (employee == null) {
            return;
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/teamTargets.fxml"));
        try {
            Parent root = fxmlLoader.load();
            TeamEmployeesController controller = fxmlLoader.getController();
            controller.editEmployee(employee);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Edit Employee Targets");
            stage.initOwner(mainBorderPane.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.resizableProperty().set(false);
            stage.show();
        } catch (IOException e) {
            System.out.println("Could not load teamTargets.fxml: " + e.getMessage());
        }
    }


}
