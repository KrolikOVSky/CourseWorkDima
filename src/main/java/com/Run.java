package com;

import com.frontEnd.OnCloseRequest;
import com.frontEnd.TableVisual;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Run extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        var mainWindow = new TableVisual();
        Global.initialize(mainWindow.getMainBoxOfElements());
        Global.primaryStage.setResizable(true);
        Global.primaryStage.setHeight(600);
        Global.primaryStage.setTitle("Program to work with database of university stuff");
        Global.primaryStage.getIcons().add(new Image("/com/images/icon.png"));
        Global.primaryStage.setOnCloseRequest(event -> {
            Platform.setImplicitExit(false);
            if (Global.changed) {
                if (new OnCloseRequest().show()) Platform.setImplicitExit(true);
                else event.consume();
            } else Platform.setImplicitExit(true);
        });
        Global.primaryStage.show();
    }
}
