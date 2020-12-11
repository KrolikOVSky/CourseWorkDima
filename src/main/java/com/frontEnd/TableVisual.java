package com.frontEnd;

import com.Global;
import com.backEnd.Schedule;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.StringConverter;

import javax.security.auth.callback.Callback;

import static com.Global.*;


public class TableVisual {
    private final BorderPane mainBoxOfElements = new BorderPane();
    private final TableColumn<Schedule, String> depCodeCol = new TableColumn<>("Department Code");
    private final TableColumn<Schedule, String> branchNumCol = new TableColumn<>("Branch number");
    private final TableColumn<Schedule, String> posNameCol = new TableColumn<>("Position");
    private final TableColumn<Schedule, Integer> countPosCol = new TableColumn<>("Position quantity");

    public TableVisual() {
        // Table
        {
            var width = 250;
            depCodeCol.setCellValueFactory(new PropertyValueFactory<>("departmentCode"));
            depCodeCol.setPrefWidth(width);

            branchNumCol.setCellValueFactory(new PropertyValueFactory<>("branchNumber"));
            branchNumCol.setPrefWidth(width);

            posNameCol.setCellValueFactory(new PropertyValueFactory<>("positionName"));
            posNameCol.setPrefWidth(width);

            countPosCol.setCellValueFactory(new PropertyValueFactory<>("countPositions"));
            countPosCol.setPrefWidth(width);

            mainTable.setPrefHeight(250);
            mainTable.setEditable(true);
            mainTable.getColumns().add(depCodeCol);
            mainTable.getColumns().add(branchNumCol);
            mainTable.getColumns().add(posNameCol);
            mainTable.getColumns().add(countPosCol);
            mainTable.setItems(schedules.getSchedules());
            mainTable.setOnMouseClicked(event -> remove(event));
            setEditable();
//            tableRoot.getChildren().add(mainTable);
        }
    }

    public BorderPane getMainBoxOfElements() {
        mainBoxOfElements.setCenter(mainTable);
        return mainBoxOfElements;
    }

    public void remove(MouseEvent event){
        if (event.getButton() == MouseButton.SECONDARY) {
            Schedule currentRoute = mainTable.getSelectionModel().getSelectedItem();
            if (currentRoute != null) {
                ContextMenu contextMenu = new ContextMenu();
                MenuItem removeItem = new MenuItem(String.format("Remove %s's element", currentRoute.getId()));
                removeItem.setOnAction(event1 -> schedules.remove(currentRoute.getId()));
                contextMenu.getItems().add(removeItem);
                mainTable.setContextMenu(contextMenu);
            }
        }
    }

    public void setEditable() {
        depCodeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        depCodeCol.setOnEditCommit(event -> {
            var id = (event.getTableView().getItems().get(event.getTablePosition().getRow())).getId();
            schedules.getById(id).setDepartmentCode(event.getNewValue());
            changed = true;
        });

        branchNumCol.setCellFactory(TextFieldTableCell.forTableColumn());
        branchNumCol.setOnEditCommit(event -> {
            var id = (event.getTableView().getItems().get(event.getTablePosition().getRow())).getId();
            schedules.getById(id).setBranchNumber(event.getNewValue());
            changed = true;
        });

        posNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        posNameCol.setOnEditCommit(event -> {
            var id = (event.getTableView().getItems().get(event.getTablePosition().getRow())).getId();
            schedules.getById(id).setPositionName(event.getNewValue());
            changed = true;
        });

        countPosCol.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<>() {
            @Override
            public String toString(Integer object) {
                return String.valueOf(object);
            }

            @Override
            public Integer fromString(String string) {
                return Integer.parseInt(string.replaceAll("[^0-9]", ""));
            }
        }));
        countPosCol.setOnEditCommit(event -> {
            var id = (event.getTableView().getItems().get(event.getTablePosition().getRow())).getId();
            schedules.getById(id).setCountPositions(event.getNewValue());
            changed = true;
        });
    }
}
