package datamodel;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Employee {
    private int id;
    private SimpleStringProperty name;
    private Team team;
    private SimpleBooleanProperty isFullTime;
    private SimpleIntegerProperty totalHours;
    private SimpleIntegerProperty saturdayHours;
    private SimpleIntegerProperty sundayHours;
    private SimpleIntegerProperty phHours;
    private SimpleIntegerProperty phCredit;
    private SimpleIntegerProperty shiftChanges;

    public Employee(String name, Team team, Boolean isFullTime, int totalHours,
                    int saturdayHours, int sundayHours, int phHours, int phCredit, int shiftChanges) {
        this.name = new SimpleStringProperty(name);
        this.team = team;
        this.isFullTime = new SimpleBooleanProperty(isFullTime);
        this.totalHours = new SimpleIntegerProperty(totalHours);
        this.saturdayHours = new SimpleIntegerProperty(saturdayHours);
        this.sundayHours = new SimpleIntegerProperty(sundayHours);
        this.phHours = new SimpleIntegerProperty(phHours);
        this.phCredit = new SimpleIntegerProperty(phCredit);
        this.shiftChanges = new SimpleIntegerProperty(shiftChanges);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public boolean isIsFullTime() {
        return isFullTime.get();
    }

    public SimpleBooleanProperty isFullTimeProperty() {
        return isFullTime;
    }

    public void setIsFullTime(boolean isFullTime) {
        this.isFullTime.set(isFullTime);
    }

    public int getTotalHours() {
        return totalHours.get();
    }

    public SimpleIntegerProperty totalHoursProperty() {
        return totalHours;
    }

    public void setTotalHours(int totalHours) {
        this.totalHours.set(totalHours);
    }

    public int getSaturdayHours() {
        return saturdayHours.get();
    }

    public SimpleIntegerProperty saturdayHoursProperty() {
        return saturdayHours;
    }

    public void setSaturdayHours(int saturdayHours) {
        this.saturdayHours.set(saturdayHours);
    }

    public int getSundayHours() {
        return sundayHours.get();
    }

    public SimpleIntegerProperty sundayHoursProperty() {
        return sundayHours;
    }

    public void setSundayHours(int sundayHours) {
        this.sundayHours.set(sundayHours);
    }

    public int getPhHours() {
        return phHours.get();
    }

    public SimpleIntegerProperty phHoursProperty() {
        return phHours;
    }

    public void setPhHours(int phHours) {
        this.phHours.set(phHours);
    }

    public int getPhCredit() {
        return phCredit.get();
    }

    public SimpleIntegerProperty phCreditProperty() {
        return phCredit;
    }

    public void setPhCredit(int phCredit) {
        this.phCredit.set(phCredit);
    }

    public int getShiftChanges() {
        return shiftChanges.get();
    }

    public SimpleIntegerProperty shiftChangesProperty() {
        return shiftChanges;
    }

    public void setShiftChanges(int shiftChanges) {
        this.shiftChanges.set(shiftChanges);
    }

    public static boolean saveEmployee(Employee employee, Pane parentPane) throws IOException {
        if (employee == null) {
            return false;
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Employee");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("XML File (.xml)", "*.xml")
        );
        File selectedFile = fileChooser.showSaveDialog(parentPane.getScene().getWindow());
        if (selectedFile == null) {
            return false;
        }

        Element rootElement = new Element("employee");
        Document doc = new Document(rootElement);

        rootElement.setAttribute("name", employee.getName());
        rootElement.setAttribute("isFullTime", String.valueOf(employee.isIsFullTime()));
        rootElement.setAttribute("totalHours", String.valueOf(employee.getTotalHours()));
        rootElement.setAttribute("saturdayHours", String.valueOf(employee.getSaturdayHours()));
        rootElement.setAttribute("sundayHours", String.valueOf(employee.getSundayHours()));
        rootElement.setAttribute("phHours", String.valueOf(employee.getPhHours()));
        rootElement.setAttribute("phCredit", String.valueOf(employee.getShiftChanges()));

        Team team = employee.getTeam();
        Element teamElement = new Element("team");
        teamElement.setAttribute("name", team.getTeamName());
        teamElement.setAttribute("startWeek", String.valueOf(team.getStartWeek()));
        rootElement.addContent(teamElement);

        Roster roster = team.getRoster();
        Element rosterElement = new Element("roster");
        rosterElement.setAttribute("name", roster.getName());
        teamElement.addContent(rosterElement);

        Element weeksElement = new Element("weeks");
        rosterElement.addContent(weeksElement);

        for (Week currentWeek : roster.getWeeks()) {
            // week element
            Element weekElement = new Element("week");
            weekElement.setAttribute("number", String.valueOf(currentWeek.getNumber()));
            weekElement.setAttribute("rosteredHours", String.valueOf(currentWeek.getRosteredHours()));
            weekElement.setAttribute("saturdayHours", String.valueOf(currentWeek.getSaturdayHours()));
            weekElement.setAttribute("sundayHours", String.valueOf(currentWeek.getSundayHours()));
            weeksElement.addContent(weekElement);
        }

        XMLOutputter xmlOutput = new XMLOutputter();

        // write xml
        xmlOutput.setFormat(Format.getPrettyFormat());
        xmlOutput.output(doc, new FileWriter(selectedFile));

        return true;
    }

    @Override
    public String toString() {
        return this.name.get();
    }
}
