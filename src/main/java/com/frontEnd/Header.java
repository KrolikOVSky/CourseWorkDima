package com.frontEnd;

import com.Global;
import com.backEnd.ResultOne;
import com.backEnd.Schedule;
import com.backEnd.Schedules;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;

import static com.Global.*;
import static java.lang.Double.MAX_VALUE;

public class Header {

    private final MenuBar mainMenuBar = new MenuBar();

    private final Menu fileMenu = new Menu("File");
    private final Menu editMenu = new Menu("Edit");
    private final Menu helpMenu = new Menu("Help");
    private final Menu pushCommands = new Menu("Push commands");

    private final MenuItem newItem = new MenuItem("New");
    private final MenuItem openItem = new MenuItem("Open");
    private final MenuItem saveAsItem = new MenuItem("Save as...");
    private final MenuItem saveItem = new MenuItem("Save");
    private final MenuItem closeItem = new MenuItem("Close");

    private final MenuItem helpItem = new MenuItem("Help");
    private final MenuItem aboutItem = new MenuItem("About...");

    private final MenuItem showAll = new MenuItem("Show all elements");
    private final MenuItem add = new MenuItem("Add new element");
    private final MenuItem sort = new MenuItem("Sort elements");
    private final MenuItem result_1 = new MenuItem("Result 1");
    private final MenuItem result_2 = new MenuItem("Result 2");
    private final CustomMenuItem remByCondition = new CustomMenuItem();
    private final CustomMenuItem filter = new CustomMenuItem();
    private final CustomMenuItem selection = new CustomMenuItem();

    public Header() {

//      File
        {
//              New
            {
                newItem.setOnAction(event -> {
                    if (Global.changed) {
                        if (new OnCloseRequest().show()) Global.newSource();
                    } else {
                        Global.newSource();
                    }
                });
                newItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
            }

//              Open
            {
                openItem.setOnAction(event -> {
                    if (Global.changed) {
                        if (new OnCloseRequest().show()) Global.openAction();
                    } else {
                        Global.openAction();
                    }
                });
                openItem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
            }

//              Save As...
            {
                saveAsItem.setOnAction(event -> Global.saveAction());
                saveAsItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));
            }

//              Save
            {
                saveItem.setOnAction(event -> {
                    if (!Global.path.equals("")) {
                        Global.fromListToFile();
                    } else {
                        Global.saveAction();
                    }
                });
                saveItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
            }

//              Close
            {
                var eventType = new EventType<>();
                closeItem.setOnAction(event -> Global.primaryStage.getOnCloseRequest().handle(new WindowEvent(Global.primaryStage.getScene().getWindow(), eventType)));
            }

            fileMenu.getItems().add(newItem);
            fileMenu.getItems().add(openItem);
            fileMenu.getItems().add(new SeparatorMenuItem());
            fileMenu.getItems().add(saveItem);
            fileMenu.getItems().add(saveAsItem);
            fileMenu.getItems().add(new SeparatorMenuItem());
            fileMenu.getItems().add(closeItem);
        }

//      Edit
        {


//          Show All
            {
                showAll.setOnAction(event -> {
                    if (schedules != null) {
                        addToTable(schedules);
                    }
                });
            }

//          Add
            {
                add.setOnAction(event -> {
                    if (schedules != null) {
                        ModalWindow modalWindow = new ModalWindow("Add element");
                        VBox addBox = new VBox(10);
                        TextField depCodeInput = new TextField();
                        TextField branchNumberInput = new TextField();
                        TextField positionNameInput = new TextField();
                        TextField positionCountInput = new TextField();
                        Button commit = new Button("Add element");
                        // Add box
                        {
                            var width = 300;
                            // Department code input
                            {
                                depCodeInput.setPromptText("Enter department code ");
                                depCodeInput.setPrefWidth(width);
                            }

                            // Branch number input
                            {
                                branchNumberInput.setPromptText("Enter branch number ");
                                branchNumberInput.setPrefWidth(width);
                            }

                            // Position name input
                            {
                                positionNameInput.setPromptText("Enter position name ");
                                positionNameInput.setPrefWidth(width);
                            }

                            // Position counts input
                            {
                                positionCountInput.setPromptText("Enter quantity of positions ");
                                positionCountInput.setPrefWidth(width);
                                positionCountInput.textProperty().addListener((observable, oldValue, newValue) -> {
                                    if (!newValue.matches("\\d*")) {
                                        positionCountInput.setText(newValue.replaceAll("[^\\d]", ""));
                                    }
                                });
                            }

                            // Commit button
                            {
                                commit.setOnAction(e -> {
                                    if (!depCodeInput.getText().isEmpty() &&
                                            !branchNumberInput.getText().isEmpty() &&
                                            !positionNameInput.getText().isEmpty() &&
                                            !positionCountInput.getText().isEmpty()
                                    ) {
                                        schedules.add(new Schedule(depCodeInput.getText(),
                                                branchNumberInput.getText(),
                                                positionNameInput.getText(),
                                                Integer.parseInt(positionCountInput.getText())));
                                        modalWindow.close();
                                    }
                                });
                            }

                            addBox.setAlignment(Pos.CENTER);
                            addBox.setPadding(new Insets(0, 0, 10, 0));
                            addBox.getChildren().add(depCodeInput);
                            addBox.getChildren().add(branchNumberInput);
                            addBox.getChildren().add(positionNameInput);
                            addBox.getChildren().add(positionCountInput);
                            addBox.getChildren().add(commit);
                        }
                        modalWindow.setMainWorkSpace(addBox);
                        modalWindow.show();
                    }
                });
            }

//          Result 1
            {
                result_1.setOnAction(event -> {
                    if (schedules != null && !schedules.getSchedules().isEmpty()) {
//                            ScrollPane scrollBox = new ScrollPane();
//                            HBox resultTable = new HBox();
//                            VBox posNameBox = new VBox();
                        BorderPane mainBox = new BorderPane();
                        TableView<ResultOne> resultTable = new TableView<>();
                        TableColumn<ResultOne, String> posNameCol = new TableColumn<>("Position");
                        TableColumn<ResultOne, Integer> posCountCol = new TableColumn<>("Position's quantity");//countOfPositions

                        posNameCol.setCellValueFactory(new PropertyValueFactory<>("position"));
                        posCountCol.setCellValueFactory(new PropertyValueFactory<>("countOfPositions"));

                        resultTable.getColumns().add(posNameCol);
                        resultTable.getColumns().add(posCountCol);
                        resultTable.setItems(schedules.result1());

                        mainBox.setCenter(resultTable);
                        mainBox.setPadding(new Insets(0,0,10,0));

                        ModalWindow window = new ModalWindow("Result 1");
                        window.setMainWorkSpace(mainBox);
                        window.show();
                    }
                });

            }

//          Result 2
            {
                result_2.setOnAction(event -> {
                    if(schedules != null && !schedules.getSchedules().isEmpty()){
                        VBox content = new VBox();
                        content.setAlignment(Pos.CENTER_LEFT);
                        content.setPadding(new Insets(0,0,10,0));
                        content.setSpacing(10);
                        content.getChildren().add(new Label(String.format("Total number of positions are %d", schedules.result2())));
                        ModalWindow modalWindow = new ModalWindow("Result 2");
                        modalWindow.setMainWorkSpace(content);
                        modalWindow.show();
                    }
                });
            }

            editMenu.getItems().add(showAll);
            editMenu.getItems().add(new SeparatorMenuItem());
            editMenu.getItems().add(add);
            editMenu.getItems().add(new SeparatorMenuItem());
            editMenu.getItems().add(result_1);
            editMenu.getItems().add(result_2);
        }

//      Push Commands
        {

//          Sort
            {
                sort.setOnAction(event -> {
                    if( schedules != null && !schedules.getSchedules().isEmpty()){
                        schedules = schedules.sort();
                    }
                });
            }

//          Remove by condition
            {
                TextField inputRemoveCondition = new TextField();
                ComboBox<String> positions = new ComboBox<>();
                Button btnRemove = new Button();
                HBox removeBox = new HBox();

                //Button Remove ++
                {
                    btnRemove.setText("Remove by condition");
                    btnRemove.setMaxSize(MAX_VALUE, MAX_VALUE);
                    btnRemove.setOnAction(event -> {
                        if (!inputRemoveCondition.getText().equals("") && schedules != null && !schedules.getSchedules().isEmpty()) {
                            schedules.removeByCondition(inputRemoveCondition.getText());
                            inputRemoveCondition.clear();
                        }
                    });
                }

                //Remove Condition ++
                {
                    inputRemoveCondition.setPromptText("Enter remove condition");
//                    inputRemoveCondition.setPrefWidth(250);
//                    inputRemoveCondition.setMaxSize(MAX_VALUE, MAX_VALUE);
                    inputRemoveCondition.setOnKeyPressed(event -> {
                        if (event.getCode() == KeyCode.ENTER) btnRemove.getOnAction().handle(new ActionEvent());
                    });


                }

                remByCondition.setContent(new HBox(10, inputRemoveCondition, btnRemove));
            }

//          Filter
            {
                TextField input = new TextField();
                Button confirm = new Button("Filter");

                // Input
                {
                    input.setPromptText("Enter filter");
                    input.setOnAction(event -> confirm.getOnAction().handle(new ActionEvent()));
                }

                // Confirm btn
                {
                    confirm.setOnAction(event -> {
                        if(schedules != null && !schedules.getSchedules().isEmpty() && !input.getText().equals("")){
                            addToTable(schedules.filter(input.getText()));
                        }
                    });
                }

                filter.setContent(new HBox(10, input, confirm));
            }

//          Selection
            {
                Label caption = new Label("Select by min and max position's count");
                TextField minInput = new TextField();
                TextField maxInput = new TextField();
                Button selectBtn = new Button("Commit");

                // Caption
                {

                }

                // Min input
                {
                    minInput.setPromptText("Min");
                    minInput.textProperty().addListener((observable, oldValue, newValue) -> {
                        if (!newValue.matches("\\d*")) {
                            minInput.setText(newValue.replaceAll("[^\\d]", ""));
                        }
                    });
                }

                // Max Input
                {
                    maxInput.setPromptText("Max");
                    maxInput.textProperty().addListener((observable, oldValue, newValue) -> {
                        if (!newValue.matches("\\d*")) {
                            maxInput.setText(newValue.replaceAll("[^\\d]", ""));
                        }
                    });
                    maxInput.setOnAction(event -> selectBtn.getOnAction().handle(new ActionEvent()));
                }

                // Apply selection button
                {
                    selectBtn.setMaxWidth(MAX_VALUE);
                    selectBtn.setOnAction(event -> {
                        if(schedules != null && !schedules.getSchedules().isEmpty() && !minInput.getText().equals("") && !maxInput.getText().equals("")){
                            addToTable(schedules.selectionByMinMaxPositionCounts(Integer.parseInt(minInput.getText()), Integer.parseInt(maxInput.getText())));
                            minInput.clear();
                            maxInput.clear();
                        }
                    });
                }

                VBox vSelBox = new VBox(10, caption, minInput, maxInput, selectBtn);
                vSelBox.setAlignment(Pos.CENTER);
                selection.setContent(vSelBox);
            }

            pushCommands.getItems().add(sort);
            pushCommands.getItems().add(remByCondition);
            pushCommands.getItems().add(filter);
            pushCommands.getItems().add(selection);
        }

//      Help
        {
//              Help
            {
                helpItem.setOnAction(event -> {
                    HelpWindow window = new HelpWindow();
                    window.show();
                });
                helpItem.setAccelerator(new KeyCodeCombination(KeyCode.F1));
            }

//              About
            {
                aboutItem.setOnAction(event ->
                {
                    AboutWindow window = new AboutWindow();
                    window.show();
                });
            }

            helpMenu.getItems().addAll(helpItem, new SeparatorMenuItem(), aboutItem);
        }

        mainMenuBar.getMenus().add(fileMenu);
        mainMenuBar.getMenus().add(editMenu);
        mainMenuBar.getMenus().add(pushCommands);
        mainMenuBar.getMenus().add(helpMenu);

    }

    public MenuBar getMainMenuBar() {
        return mainMenuBar;
    }

    private void addToTable(Schedules schedules) {
        mainTable.setItems(schedules.getSchedules());
    }
}