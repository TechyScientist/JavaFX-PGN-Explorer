package com.johnnyconsole.pgnexplorer;

import javafx.scene.image.Image;

import java.util.Objects;

import static com.johnnyconsole.pgnexplorer.Explorer.SQUARE_SIZE;

public class PieceImage {

    public static final Image WK = new Image(Objects.requireNonNull(PieceImage.class.getResourceAsStream("/img/pieces/white/king.png")),
            SQUARE_SIZE, SQUARE_SIZE, true, true),
            WQ = new Image(Objects.requireNonNull(PieceImage.class.getResourceAsStream("/img/pieces/white/queen.png")),
                    SQUARE_SIZE, SQUARE_SIZE, true, true),
            WN = new Image(Objects.requireNonNull(PieceImage.class.getResourceAsStream("/img/pieces/white/knight.png")),
                    SQUARE_SIZE, SQUARE_SIZE, true, true),
            WB = new Image(Objects.requireNonNull(PieceImage.class.getResourceAsStream("/img/pieces/white/bishop.png")),
                    SQUARE_SIZE, SQUARE_SIZE, true, true),
            WR = new Image(Objects.requireNonNull(PieceImage.class.getResourceAsStream("/img/pieces/white/rook.png")),
                    SQUARE_SIZE, SQUARE_SIZE, true, true),
            WP = new Image(Objects.requireNonNull(PieceImage.class.getResourceAsStream("/img/pieces/white/pawn.png")),
                    SQUARE_SIZE, SQUARE_SIZE, true, true),
            BK = new Image(Objects.requireNonNull(PieceImage.class.getResourceAsStream("/img/pieces/black/king.png")),
                    SQUARE_SIZE, SQUARE_SIZE, true, true),
            BQ = new Image(Objects.requireNonNull(PieceImage.class.getResourceAsStream("/img/pieces/black/queen.png")),
                    SQUARE_SIZE, SQUARE_SIZE, true, true),
            BN = new Image(Objects.requireNonNull(PieceImage.class.getResourceAsStream("/img/pieces/black/knight.png")),
                    SQUARE_SIZE, SQUARE_SIZE, true, true),
            BB = new Image(Objects.requireNonNull(PieceImage.class.getResourceAsStream("/img/pieces/black/bishop.png")),
                    SQUARE_SIZE, SQUARE_SIZE, true, true),
            BR = new Image(Objects.requireNonNull(PieceImage.class.getResourceAsStream("/img/pieces/black/rook.png")),
                    SQUARE_SIZE, SQUARE_SIZE, true, true),
            BP = new Image(Objects.requireNonNull(PieceImage.class.getResourceAsStream("/img/pieces/black/pawn.png")),
                    SQUARE_SIZE, SQUARE_SIZE, true, true);

    ;
}
