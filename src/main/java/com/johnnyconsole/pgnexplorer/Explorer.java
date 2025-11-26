package com.johnnyconsole.pgnexplorer;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import static javafx.scene.text.FontWeight.BOLD;
import static javafx.scene.text.FontPosture.REGULAR;
import static com.johnnyconsole.pgnexplorer.PieceImage.*;

public class Explorer extends Application {

    private static final StackPane[][] squares = new StackPane[8][8];
    private static final Color SQUARE_LIGHT = Color.WHITE,
                        SQUARE_DARK = Color.GREEN;
    public static final double SQUARE_SIZE = 100,
                                BUTTON_HEIGHT = 50;

    @Override
    public void start(Stage ps) {
        GridPane root = new GridPane(),
                board = new GridPane(),
                control = new GridPane();
        root.setPadding(new Insets(20, 20, 0, 0));
        control.setPadding(new Insets(20));
        root.setHgap(10);
        control.setHgap(10);
        root.setVgap(10);
        control.setVgap(10);

        //Create the square nodes
        for(int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                StackPane square = new StackPane();
                square.setBackground(
                        new Background(
                        new BackgroundFill(
                             i % 2 == j % 2 ? SQUARE_LIGHT : SQUARE_DARK,
                             null, null
                        )
                    )
                );
                square.setMinWidth(SQUARE_SIZE);
                square.setMinHeight(SQUARE_SIZE);
                squares[i - 1][j - 1] = square;
                board.add(square, j, i);
            }
        }

        //Add the rank and file labels
        for (int i = 1; i <= 8; i++) {
            Label rank = new Label(String.valueOf(Math.abs(8 - i + 1))),
                    file = new Label((char)((i - 1) + 'a') + "");

            rank.setPadding(new Insets(10));
            file.setPadding(rank.getPadding());
            rank.setTextFill(SQUARE_DARK);
            file.setTextFill(SQUARE_DARK);
            rank.setFont(Font.font(null, BOLD, REGULAR, 16));
            file.setFont(rank.getFont());
            GridPane.setHalignment(file, HPos.CENTER);
            GridPane.setValignment(rank, VPos.CENTER);

            board.add(rank, 0, i);
            board.add(file, i, 9);
        }

        resetBoard();
        root.add(board, 0, 0);

        //Create Control Panel
        Button openPGN = new Button("Import PGN"),
                nextPly = new Button("Next Ply"),
                nextMove = new Button("Next Move"),
                end = new Button("End"),
                prevPly = new Button("Previous Ply"),
                prevMove = new Button("Previous Move"),
                start = new Button("Start"),
                reset = new Button("Reset to Starting Position"),
                exit = new Button("Exit");

        openPGN.setMaxWidth(Double.MAX_VALUE);
        openPGN.setPrefHeight(BUTTON_HEIGHT);
        nextPly.setMaxWidth(Double.MAX_VALUE);
        nextPly.setPrefHeight(BUTTON_HEIGHT);
        nextMove.setMaxWidth(Double.MAX_VALUE);
        nextMove.setPrefHeight(BUTTON_HEIGHT);
        prevPly.setMaxWidth(Double.MAX_VALUE);
        prevPly.setPrefHeight(BUTTON_HEIGHT);
        prevMove.setMaxWidth(Double.MAX_VALUE);
        prevMove.setPrefHeight(BUTTON_HEIGHT);
        start.setMaxWidth(Double.MAX_VALUE);
        start.setPrefHeight(BUTTON_HEIGHT);
        end.setMaxWidth(Double.MAX_VALUE);
        end.setPrefHeight(BUTTON_HEIGHT);
        reset.setMaxWidth(Double.MAX_VALUE);
        reset.setPrefHeight(BUTTON_HEIGHT);
        exit.setMaxWidth(Double.MAX_VALUE);
        exit.setPrefHeight(BUTTON_HEIGHT);

        reset.setOnAction(e -> resetBoard());

        control.add(openPGN, 0, 0, 2, 1);
        control.add(prevPly, 0, 1);
        control.add(nextPly, 1, 1);
        control.add(prevMove, 0, 2);
        control.add(nextMove, 1, 2);
        control.add(start, 0, 3);
        control.add(end, 1, 3);
        control.add(reset, 0, 4, 2, 1);
        control.add(exit, 0, 5, 2, 1);

        GridPane.setValignment(control, VPos.CENTER);
        root.add(control, 1, 0);

        ps.setTitle("PGN Explorer");
        ps.setScene(new Scene(root));
        ps.show();
    }

    private void resetBoard() {
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares.length; j++) {
                squares[i][j].getChildren().clear();
            }
        }

        //Add pieces
        for (int i = 1; i <= 8; i++) {
            squares[6][i - 1].getChildren().add(new ImageView(WP));
            squares[1][i - 1].getChildren().add(new ImageView(BP));
        }

        squares[0][0].getChildren().add(new ImageView(BR));
        squares[0][7].getChildren().add(new ImageView(BR));
        squares[7][0].getChildren().add(new ImageView(WR));
        squares[7][7].getChildren().add(new ImageView(WR));

        squares[0][1].getChildren().add(new ImageView(BN));
        squares[0][6].getChildren().add(new ImageView(BN));
        squares[7][1].getChildren().add(new ImageView(WN));
        squares[7][6].getChildren().add(new ImageView(WN));

        squares[0][2].getChildren().add(new ImageView(BB));
        squares[0][5].getChildren().add(new ImageView(BB));
        squares[7][2].getChildren().add(new ImageView(WB));
        squares[7][5].getChildren().add(new ImageView(WB));

        squares[0][3].getChildren().add(new ImageView(BQ));
        squares[7][3].getChildren().add(new ImageView(WQ));

        squares[0][4].getChildren().add(new ImageView(BK));
        squares[7][4].getChildren().add(new ImageView(WK));
    }
    @SuppressWarnings("unused")
    public static void main(String[] args) {
        launch(args);
    }

}
