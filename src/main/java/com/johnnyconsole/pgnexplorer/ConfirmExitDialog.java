package com.johnnyconsole.pgnexplorer;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ConfirmExitDialog extends Application {

    private final Stage prevWindow;

    protected ConfirmExitDialog(Stage prevWindow) {
        this.prevWindow = prevWindow;
        prevWindow.hide();
    }

    @Override
    public void start(Stage ps) {
        GridPane root = new GridPane();
        root.setPadding(new Insets(20));
        root.setHgap(10);
        root.setVgap(10);

        root.add(new Label("Are you sure you want to quit?"),
                0, 0, 2, 1);

        Button yes = new Button("Yes"),
                no = new Button("No");

        yes.setPrefWidth(90);
        yes.setPrefHeight(Explorer.BUTTON_HEIGHT);
        no.setPrefWidth(90);
        no.setPrefHeight(Explorer.BUTTON_HEIGHT);

        root.addRow(1, yes, no);

        no.setOnAction(e -> {
            ps.close();
            prevWindow.show();
        });
        yes.setOnAction(e -> {
            prevWindow.close();
            ps.close();
        });

        ps.setTitle("Confirmation");
        ps.setScene(new Scene(root));
        ps.show();
        System.out.println((ps.getWidth() - 40) / 2);
    }

}
