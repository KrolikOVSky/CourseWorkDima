package com.backEnd;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Schedules {

    private ObservableList<Schedule> schedules;

    private Long count = 1L;

    public Schedules() {
        this.schedules = FXCollections.observableList(new ArrayList<>());
    }

    public Schedules(ObservableList<Schedule> schedules) {
        this.schedules = schedules;
    }

    public void clear() {
        this.count = 1L;
        this.schedules.clear();
    }

    public ObservableList<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(ObservableList<Schedule> schedules) {
        this.schedules = schedules;
    }

    public Schedule getById(Long id) {
        for (var el : schedules) {
            if (el.getId() == id) return el;
        }
        return null;
    }

    public void add(Schedule schedule) {
        schedule.setId(count++);
        if (getById(schedule.getId()) != null) return;
        schedules.add(schedule);
    }

    public boolean remove(long id) {
        if (schedules == null || getById(id) == null) return false;
        return schedules.remove(getById(id));
    }

    public int getSize() {
        return schedules.size();
    }

    public Schedules sort() {
        Comparator<Schedule> comparator = Comparator.comparing(Schedule::getBranchNumber).thenComparing(Schedule::getDepartmentCode);
        Schedules schedules = new Schedules(this.schedules);
        schedules.schedules.sort(comparator);
        return schedules;
    }

    public Schedules betWeenStops(int min, int max) {
        var schedules = new Schedules();
        for (var el : this.schedules) {
            if (el.getCountPositions() > min && el.getCountPositions() < max) schedules.add(el);
        }
        return schedules;
    }

    // result 1
    public ObservableList<ResultOne> result1() {
        ObservableList<String> typesList = getPositionsList();
        ObservableList<ResultOne> result = FXCollections.observableArrayList(new ArrayList<>());
/*
        for (var schedule : getSchedules()) {
            boolean flag = false;
            for (var el : typesList) {
                if (schedule.getPositionName().equals(el)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) typesList.add(schedule.getPositionName());
        }*/

        for (var el : typesList) {
            var count = 0;
            for (var schedule : getSchedules()) {
                if (el.equals(schedule.getPositionName())) {
                    count += schedule.getCountPositions();
                }
            }
            result.add(new ResultOne(el, count));
        }
        return result;
    }

    // result 2
    public int result2() {
        List<String> tempList = new ArrayList<>();
        for (var schedule : getSchedules()) {
            boolean flag = false;
            for (var el : tempList) {
                if (schedule.getBranchNumber().equals(el)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) tempList.add(schedule.getBranchNumber());
        }
        return tempList.size();
    }

    public ObservableList<String> getPositionsList() {
        ObservableList<String> positionsList = FXCollections.observableList(new ArrayList<>());
        for (var schedule : getSchedules()) {
            boolean flag = false;
            for (var el : positionsList) {
                if (schedule.getPositionName().equals(el)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) positionsList.add(schedule.getPositionName());
        }
        return positionsList;
    }

    public void removeByCondition(String value) {
        this.getSchedules().removeIf(n -> (n.getPositionName().equals(value)));
    }

    public Schedules filter(String type) {
        var schedules = new Schedules();
        for (var el : this.getSchedules()) {
            if (el.getDepartmentCode().toLowerCase().startsWith(type.toLowerCase())) {
                schedules.add(el);
            }
        }
        return schedules;
    }

    public Schedules selectionByMinMaxPositionCounts(int min, int max) {
        Schedules schedules = new Schedules();
        for (var el : this.schedules) {
            if (el.getCountPositions() > min && el.getCountPositions() < max) schedules.add(el);
        }
        return schedules;
    }

}

