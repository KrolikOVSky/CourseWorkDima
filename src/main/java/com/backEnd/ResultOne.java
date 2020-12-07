package com.backEnd;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ResultOne {

    private final SimpleStringProperty position = new SimpleStringProperty();
    private final SimpleIntegerProperty countOfPositions = new SimpleIntegerProperty();

    public ResultOne(String position, int countOfPositions) {
        this.position.set(position);
        this.countOfPositions.set(countOfPositions);
    }

    public ResultOne() {
        this.position.set("");
        this.countOfPositions.set(0);
    }

    public String getPosition() {
        return position.get();
    }

    public SimpleStringProperty positionProperty() {
        return position;
    }

    public void setPosition(String position) {
        this.position.set(position);
    }

    public int getCountOfPositions() {
        return countOfPositions.get();
    }

    public SimpleIntegerProperty countOfPositionsProperty() {
        return countOfPositions;
    }

    public void setCountOfPositions(int countOfPositions) {
        this.countOfPositions.set(countOfPositions);
    }
}
