package com.backEnd;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class Schedule {

    private final SimpleLongProperty id = new SimpleLongProperty();
    private final SimpleStringProperty departmentCode = new SimpleStringProperty();
    private final SimpleStringProperty branchNumber = new SimpleStringProperty();
    private final SimpleStringProperty positionName = new SimpleStringProperty();
    private final SimpleIntegerProperty countPositions = new SimpleIntegerProperty();

    public Schedule(){
        this.departmentCode.set("");
        this.branchNumber.set("");
        this.positionName.set("");
        this.countPositions.set(0);
    }

    public Schedule(String departmentCode, String branchNumber, String positionName, int countPositions){
        this.departmentCode.set(departmentCode);
        this.branchNumber.set(branchNumber);
        this.positionName.set(positionName);
        this.countPositions.set(countPositions);
    }

    public long getId() {
        return id.get();
    }

    public SimpleLongProperty idProperty() {
        return id;
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public String getDepartmentCode() {
        return departmentCode.get();
    }

    public SimpleStringProperty departmentCodeProperty() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode.set(departmentCode);
    }

    public String getBranchNumber() {
        return branchNumber.get();
    }

    public SimpleStringProperty branchNumberProperty() {
        return branchNumber;
    }

    public void setBranchNumber(String branchNumber) {
        this.branchNumber.set(branchNumber);
    }

    public String getPositionName() {
        return positionName.get();
    }

    public SimpleStringProperty positionNameProperty() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName.set(positionName);
    }

    public int getCountPositions() {
        return countPositions.get();
    }

    public SimpleIntegerProperty countPositionsProperty() {
        return countPositions;
    }

    public void setCountPositions(int countPositions) {
        this.countPositions.set(countPositions);
    }

    @Override
    public String toString() {
        return  "\nID = " + getId() +
                "\nDepartment Code = " + getDepartmentCode() +
                "\nBranch Number = " + getBranchNumber() +
                "\nPosition Name = " + getPositionName() +
                "\nCount Positions = " + getCountPositions() +
                "\n";
    }
}
