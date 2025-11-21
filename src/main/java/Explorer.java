import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import static javafx.scene.text.FontWeight.BOLD;
import static javafx.scene.text.FontPosture.REGULAR;

public class Explorer extends Application {

    StackPane[][] squares = new StackPane[8][8];
    private static final Color SQUARE_LIGHT = Color.WHITE,
                        SQUARE_DARK = Color.GREEN;
    private static final double SQUARE_SIZE = 100;

    @Override
    public void start(Stage ps) {
        GridPane board = new GridPane();
        board.setPadding(new Insets(20, 20, 0, 0));
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
                board.add(square, i, j);
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

        ps.setTitle("PGN Explorer");
        ps.setScene(new Scene(board));
        ps.show();
    }

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        launch(args);
    }

}
