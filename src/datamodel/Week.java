package datamodel;

import javafx.beans.property.SimpleIntegerProperty;

public class Week {
    private SimpleIntegerProperty number;
    private SimpleIntegerProperty rosteredHours;
    private SimpleIntegerProperty saturdayHours;
    private SimpleIntegerProperty sundayHours;
    private SimpleIntegerProperty phHours;

    public Week(int number, int rosteredHours, int saturdayHours, int sundayHours) {
        this.number = new SimpleIntegerProperty(number);
        this.rosteredHours = new SimpleIntegerProperty(rosteredHours);
        this.saturdayHours = new SimpleIntegerProperty(saturdayHours);
        this.sundayHours = new SimpleIntegerProperty(sundayHours);
    }


    public int getNumber() {
        return number.get();
    }

    public SimpleIntegerProperty numberProperty() {
        return number;
    }

    public void setNumber(int number) {
        this.number.set(number);
    }

    public int getRosteredHours() {
        return rosteredHours.get();
    }

    public SimpleIntegerProperty rosteredHoursProperty() {
        return rosteredHours;
    }

    public void setRosteredHours(int rosteredHours) {
        this.rosteredHours.set(rosteredHours);
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

    public int getphHours() {
        return phHours.get();
    }

    public SimpleIntegerProperty phHoursProperty() {
        return phHours;
    }

    public void setPhHours(int phHours) {
        this.phHours.set(phHours);
    }
}
