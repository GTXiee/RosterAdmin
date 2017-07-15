package datamodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

public class Roster {
    private int id;
    private SimpleStringProperty name;
    private ObservableList<Week> weeks;

    public Roster(String name, ObservableList<Week> weeks) {
        this.name = new SimpleStringProperty(name);
        this.weeks = weeks;
    }

    public String getName() {
        return this.name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public ObservableList<Week> getWeeks() {
        return weeks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setWeeks(ObservableList<Week> weeks) {
        this.weeks = weeks;
    }

    @Override
    public String toString() {
        return this.name.get();
    }
}
