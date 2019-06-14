package com.example.othellogame;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class PiecesService extends Parent {

    private static final String WHITE_ID = "01";
    private static final String BLACK_ID = "02";

    static ImageView createEmpty() {
        Image emptyBack = new Image("pieces/empty.png");
        ImageView transparent = new ImageView(emptyBack);
        transparent.setFitHeight(50);
        transparent.setFitWidth(50);

        return transparent;
    }

    static void turnPieceBlack(GridPane board, double col, double row) {

        for (Node piece : board.getChildren()) {
            if (GridPane.getColumnIndex(piece) == col && GridPane.getRowIndex(piece) == row) {
                ImageView imageView = (ImageView) piece;
                imageView.setImage(new Image("pieces/black.gif"));
                imageView.setId(BLACK_ID);
            }
        }
    }

    static void turnPieceWhite(GridPane board, double col, double row) {

        for (Node piece : board.getChildren()) {
            if (GridPane.getColumnIndex(piece) == col && GridPane.getRowIndex(piece) == row) {
                ImageView imageView = (ImageView) piece;
                imageView.setImage(new Image("pieces/white.gif"));
                imageView.setId(WHITE_ID);
            }
        }
    }

    static void setOnStartingPositions(GridPane grid) {

        for (int column = 0; column < 8; column++) {
            for (int row = 0; row < 8; row++) {
                grid.add(PiecesService.createEmpty(), column, row);
            }
        }
        turnPieceBlack(grid, 4, 3);
        turnPieceBlack(grid, 3, 4);
        turnPieceWhite(grid, 3, 3);
        turnPieceWhite(grid, 4, 4);
    }

    static void clearBoard(GridPane grid) {
        int gridSize = grid.getChildren().size();

        grid.getChildren().remove(0, gridSize);
    }

    public static void flipToColor(List<Node> nodeList, GridPane grid, String currentPlayerID, String opponentID) {
        List<Node> piecesToFlip = new ArrayList<>();

        nodeList = nodeList.stream()
                .filter(el -> el.getId() != null)
                .collect(Collectors.toList());

        if (Validator.hasPlayerPieceAfterOpponents(nodeList, currentPlayerID, opponentID)) {
            for (Node node : nodeList) {
                if (node.getId().equals(opponentID)) {
                    piecesToFlip.add(node);
                }
            }
        }
        if (opponentID.equals(WHITE_ID)) {
            for (Node white : piecesToFlip) {
                turnPieceBlack(grid, GridPane.getColumnIndex(white), GridPane.getRowIndex(white));
            }
        } else if (opponentID.equals(BLACK_ID)) {
            for (Node black : piecesToFlip) {
                turnPieceWhite(grid, GridPane.getColumnIndex(black), GridPane.getRowIndex(black));
            }
        }
    }

    public static long countPieces(GridPane grid, String pieceID) {

        List<String> pieces = new ArrayList<>();

        for (Node piece : grid.getChildren()) {
            if (piece.getId() != null) {
                pieces.add(piece.getId());
            }
        }
        return pieces.stream()
                .filter(id -> id.equals(pieceID))
                .count();
    }
}