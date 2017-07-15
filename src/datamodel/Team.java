package datamodel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Team {

    private int id;
    private SimpleStringProperty teamName;
    private SimpleIntegerProperty startWeek;
    private Roster roster;

    public Team(String teamName, int startWeek, Roster roster) {
        this.teamName = new SimpleStringProperty(teamName);
        this.startWeek = new SimpleIntegerProperty(startWeek);
        this.roster = roster;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName.get();
    }

    public SimpleStringProperty teamNameProperty() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName.set(teamName);
    }

    public int getStartWeek() {
        return startWeek.get();
    }

    public SimpleIntegerProperty startWeekProperty() {
        return startWeek;
    }

    public void setStartWeek(int startWeek) {
        this.startWeek.set(startWeek);
    }

    public Roster getRoster() {
        return roster;
    }

    public void setRoster(Roster roster) {
        this.roster = roster;
    }


    @Override
    public String toString() {
        return this.getTeamName();
    }


}
