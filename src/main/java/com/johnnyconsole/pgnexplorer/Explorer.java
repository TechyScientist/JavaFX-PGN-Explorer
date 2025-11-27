package com.johnnyconsole.pgnexplorer;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.move.MoveList;
import com.github.bhlangonijr.chesslib.pgn.PgnHolder;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

import static javafx.scene.text.FontWeight.BOLD;
import static javafx.scene.text.FontPosture.REGULAR;
import static com.johnnyconsole.pgnexplorer.PieceImage.*;
import static com.johnnyconsole.pgnexplorer.GameSound.*;

public class Explorer extends Application {

    private static final StackPane[][] squares = new StackPane[8][8];
    private static final Color SQUARE_LIGHT = Color.WHITE,
                        SQUARE_DARK = Color.GREEN;
    public static final double SQUARE_SIZE = 100,
                                BUTTON_HEIGHT = 50;
    private static final Button openPGN = new Button("Import PGN"),
            nextPly = new Button("Next Ply"),
            end = new Button("End"),
            prevPly = new Button("Previous Ply"),
            start = new Button("Start"),
            reset = new Button("Reset to Starting Position"),
            exit = new Button("Exit");
    public static final Label moveCounter = new Label();
    public static final TextField positionFen = new TextField();
    private static MoveList moves;
    private static Board b;
    private static int plyIndex = 0;

    @SuppressWarnings("unused")
    @Override
    public void start(Stage ps) {
        GridPane root = new GridPane(),
                board = new GridPane(),
                control = new GridPane();
        root.setPadding(new Insets(20));
        control.setPadding(new Insets(20));
        root.setHgap(10);
        control.setHgap(10);
        root.setVgap(10);
        control.setVgap(10);

        positionFen.setEditable(false);
        positionFen.setPrefWidth(600);
        positionFen.setFont(Font.font(16));

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

        HBox fenBox = new HBox(new Label("Position FEN:"), positionFen);
        fenBox.setSpacing(10);
        fenBox.setMaxWidth(Double.MAX_VALUE);
        fenBox.setAlignment(Pos.CENTER);
        ((Label)(fenBox.getChildren().getFirst())).setFont(Font.font(16));
        GridPane.setHalignment(fenBox, HPos.CENTER);
        board.add(fenBox, 1, 11, 8, 1);


        resetBoard();
        root.add(board, 0, 0);

        //Create Control Panel
        openPGN.setMaxWidth(Double.MAX_VALUE);
        openPGN.setPrefHeight(BUTTON_HEIGHT);
        nextPly.setMaxWidth(Double.MAX_VALUE);
        nextPly.setPrefHeight(BUTTON_HEIGHT);
        prevPly.setMaxWidth(Double.MAX_VALUE);
        prevPly.setPrefHeight(BUTTON_HEIGHT);
        start.setMaxWidth(Double.MAX_VALUE);
        start.setPrefHeight(BUTTON_HEIGHT);
        end.setMaxWidth(Double.MAX_VALUE);
        end.setPrefHeight(BUTTON_HEIGHT);
        reset.setMaxWidth(Double.MAX_VALUE);
        reset.setPrefHeight(BUTTON_HEIGHT);
        exit.setMaxWidth(Double.MAX_VALUE);
        exit.setPrefHeight(BUTTON_HEIGHT);

        openPGN.setOnAction(e -> {
            try {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select a PGN File");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("PGN Files (*.pgn)", "*.pgn")
                );
                File file = fileChooser.showOpenDialog(ps);
                if (file.exists()) {
                    PgnHolder holder = new PgnHolder(file.getAbsolutePath());
                    holder.loadPgn();
                    moves = holder.getGames().getFirst().getHalfMoves();
                    plyIndex = 0;
                    startingPosition();

                    nextPly.setDisable(false);
                    end.setDisable(false);
                    reset.setDisable(false);
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        nextPly.setOnAction(e -> {
            Move move = moves.get(plyIndex++);
            b.doMove(move);
            prevPly.setDisable(false);
            start.setDisable(false);
            if(plyIndex == moves.size()) {
                nextPly.setDisable(true);
                end.setDisable(true);
            }
            else {
                nextPly.setDisable(false);
                end.setDisable(false);
            }
            String fen = b.getFen(false);
            updateBoard(move, fen.substring(0, fen.indexOf(' ')));
        });

        prevPly.setOnAction(e -> {
            nextPly.setDisable(false);
            end.setDisable(false);
            plyIndex--;
            if(plyIndex <= 0) {
                plyIndex = 0;
                startingPosition();
                prevPly.setDisable(true);
                start.setDisable(true);
                return;
            } else {
                prevPly.setDisable(false);
                start.setDisable(false);
            }
            Move move = b.undoMove();
            String fen = b.getFen(false);
            updateBoard(move, fen.substring(0, fen.indexOf(' ')));
        });

        start.setOnAction(e -> {
            while(plyIndex != 0) {
                b.undoMove();
                plyIndex--;
            }
            nextPly.setDisable(false);
            end.setDisable(false);
            prevPly.setDisable(true);
            start.setDisable(true);

            startingPosition();
        });

        end.setOnAction(e -> {
           while(plyIndex != moves.size()) {
               b.doMove(moves.get(plyIndex++));
           }
            nextPly.setDisable(true);
            end.setDisable(true);
            prevPly.setDisable(false);
            start.setDisable(false);
            String fen = b.getFen(false);
            updateBoard(moves.getLast(), fen.substring(0, fen.indexOf(' ')));
        });

        reset.setOnAction(e -> resetBoard());
        exit.setOnAction(e -> new ConfirmExitDialog(ps).start(new Stage()));

        control.add(openPGN, 0, 0, 2, 1);
        control.add(prevPly, 0, 1);
        control.add(nextPly, 1, 1);
        control.add(start, 0, 3);
        control.add(end, 1, 3);
        control.add(reset, 0, 4, 2, 1);
        control.add(exit, 0, 5, 2, 1);
        control.add(moveCounter, 0, 7, 2, 1);

        GridPane.setValignment(control, VPos.CENTER);
        root.add(control, 1, 0);

        //TODO: Add PGN move list to layout

        ps.setTitle("PGN Explorer");
        ps.setScene(new Scene(root));
        ps.show();
    }

    private void resetBoard() {
        moves = null;
        b = null;
        prevPly.setDisable(true);
        nextPly.setDisable(true);
        start.setDisable(true);
        end.setDisable(true);
        reset.setDisable(true);

        startingPosition();
    }

    private void startingPosition() {
        clearBoard();
        b = new Board();
        moveCounter.setText("Starting Position");
        positionFen.setText("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
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

    private void clearBoard() {
        for (StackPane[] rank : squares) {
            for (int j = 0; j < squares.length; j++) {
                rank[j].getChildren().clear();
            }
        }
    }

    private void updateBoard(Move move, String fen) {
        moveCounter.setText(String.format("Move Played: %s\nMove Counter:\n\tMove %d of %d\n\tPly %d of %d\n\t" +
                (b.isMated() ? "Checkmate" : b.isStaleMate() ? "Draw: Stalemate" :
                    b.isInsufficientMaterial() ? "Draw: Insufficient Material" :
                    b.isRepetition() ? "Draw: Threefold Repetition" :
                    b.isDraw() ? "Draw" : b.isKingAttacked() ? "Check: %s to move" : "%s to move"),
                ((plyIndex / 2) + 1) +  (plyIndex % 2 == 0 ? "... " : ". ") + move.getSan(),
                plyIndex % 2 == 0 ? plyIndex / 2 : (plyIndex / 2) + 1,
                moves.size() % 2 == 0 ? moves.size() / 2 : (moves.size() / 2) + 1,
                plyIndex, moves.size(),
                plyIndex % 2 == 0 ? "White" : "Black"
        ));
        String[] ranks = fen.split("/");
        clearBoard();
        for(int i = 0; i < squares.length; i++) {
            int j = 0, c = 0;
            while(j < squares.length) {
                char sq =  ranks[i].charAt(c++);
                if(Character.isDigit(sq)) {
                    j += (sq - '0');
                }
                else {
                    switch(sq) {
                        case 'p':
                            squares[i][j].getChildren().add(new ImageView(BP));
                            break;
                        case 'P':
                            squares[i][j].getChildren().add(new ImageView(WP));
                            break;
                        case 'n':
                            squares[i][j].getChildren().add(new ImageView(BN));
                            break;
                        case 'N':
                            squares[i][j].getChildren().add(new ImageView(WN));
                            break;
                        case 'b':
                            squares[i][j].getChildren().add(new ImageView(BB));
                            break;
                        case 'B':
                            squares[i][j].getChildren().add(new ImageView(WB));
                            break;
                        case 'r':
                            squares[i][j].getChildren().add(new ImageView(BR));
                            break;
                        case 'R':
                            squares[i][j].getChildren().add(new ImageView(WR));
                            break;
                        case 'k':
                            squares[i][j].getChildren().add(new ImageView(BK));
                            break;
                        case 'K':
                            squares[i][j].getChildren().add(new ImageView(WK));
                            break;
                        case 'q':
                            squares[i][j].getChildren().add(new ImageView(BQ));
                            break;
                        case 'Q':
                            squares[i][j].getChildren().add(new ImageView(WQ));
                            break;
                    }
                    j++;
                }
            }
        }
        playMoveSound(move);
        positionFen.setText(fen);
    }

    private void playMoveSound(Move move) {
        if(move.getPromotion() != Piece.NONE) SOUND_PROMOTE.play();
        if(b.isKingAttacked()) SOUND_CHECK.play();
        if(b.isMated() || b.isStaleMate() ||
                b.isInsufficientMaterial() || b.isRepetition() ||
                b.isDraw()) {
            SOUND_GAME_END.play();
            return;
        }
        if(move.getSan().contains("O")) SOUND_CASTLE.play();
        if(move.getSan().contains("x")) SOUND_CAPTURE.play();
        else SOUND_MOVE.play();
    }
    @SuppressWarnings("unused")
    static void main(String[] args) {
        launch(args);
    }

}
