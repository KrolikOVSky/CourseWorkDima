package com.frontEnd;

import com.Global;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModalWindow {
    private final Stage STAGE;

    public ModalWindow(String caption){
        this.STAGE = new Stage();
        this.STAGE.initModality(Modality.WINDOW_MODAL);
        this.STAGE.getIcons().add(Global.primaryStage.getIcons().get(0));
        this.STAGE.initOwner(Global.primaryStage);
        this.STAGE.setTitle(caption);
        this.STAGE.setMinHeight(150);
        this.STAGE.setMinWidth(250);
        this.STAGE.setResizable(false);
    }

    public void show(){
        this.STAGE.showAndWait();
    }

    public void close(){
        this.STAGE.close();
    }

    public void setMainWorkSpace(Node node) {
        BorderPane mainWorkSpace = new BorderPane();
        Button closeBtn = new Button("Cancel");
        closeBtn.setOnAction(event -> this.STAGE.close());

        closeBtn.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ESCAPE) closeBtn.getOnAction().handle(new ActionEvent());
        });

        mainWorkSpace.setBottom(closeBtn);
        mainWorkSpace.setCenter(node);
        mainWorkSpace.setPadding(new Insets(10));
        BorderPane.setAlignment(closeBtn, Pos.CENTER);
        this.STAGE.setScene(new Scene(mainWorkSpace));
    }
}
