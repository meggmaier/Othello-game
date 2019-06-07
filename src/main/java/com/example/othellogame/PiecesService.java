package com.example.othellogame;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Arrays;
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

    static boolean isMoveLegal(int col, int row, GridPane grid) {


        if (col < 0 || col > 7) {
            System.out.println("Out of board");
            return false;
        }

        if (row < 0 || row > 7) {
            System.out.println("Out of board");
            return false;
        }

        String id = GridHelper.getIdByColumnRowIndex(col, row, grid).getId();

        if (id != null) {
            System.out.println("Already taken");
            return false;
        }

        if (!hasAtLeastOneEnemyInRange(col, row, grid)) {
            System.out.println("No neighbor as an enemy");
            return false;
        }


        if (hasAtLeastOneEnemyInRange(col, row, grid)) {
            return getBlackAfterWhiteListBoolean(col, row, grid).stream()
                    .anyMatch(hasBlack -> hasBlack);
        }

        return true;
    }

    private static boolean hasAtLeastOneEnemyInRange(int col, int row, GridPane grid) {
        List<String> neighborsIds = Arrays.asList(
                GridHelper.getNeighbors00(col, row, grid).get(0).getId(),
                GridHelper.getNeighbors45(col, row, grid).get(0).getId(),
                GridHelper.getNeighbors90(col, row, grid).get(0).getId(),
                GridHelper.getNeighbors135(col, row, grid).get(0).getId(),
                GridHelper.getNeighbors180(col, row, grid).get(0).getId(),
                GridHelper.getNeighbors225(col, row, grid).get(0).getId(),
                GridHelper.getNeighbors270(col, row, grid).get(0).getId(),
                GridHelper.getNeighbors315(col, row, grid).get(0).getId()
        );

        return neighborsIds.stream()
                .filter(id -> id != null)
                .anyMatch(id -> id.equals(WHITE_ID));
    }


    public static boolean hasBlackAfterWhite(List<Node> idList) {
        if(idList.isEmpty()){
            return false;
        }

        boolean hasBlack = false;
        String firstPiece = idList.get(0).getId();


        loop:
        while (firstPiece != null && firstPiece.equals(WHITE_ID)) {

            for (int i = 0; i < idList.size() - 1; i++) {
                String nextPiece = idList.get(i + 1).getId();

                if (nextPiece == null) {
                    return hasBlack;
                }

                if (nextPiece.equals(BLACK_ID)) {
                    hasBlack = true;
                    break loop;
                }
            }
        }

        return hasBlack;
    }

    public static boolean hasWhiteAfterBlack(List<Node> idList) {

        boolean hasWhite = false;
        String firstPiece = idList.get(0).getId();


        loop:
        while (firstPiece != null && firstPiece.equals(BLACK_ID)) {

            for (int i = 0; i < idList.size() - 1; i++) {
                String nextPiece = idList.get(i + 1).getId();

                if (nextPiece == null) {
                    return hasWhite;
                }

                if (nextPiece.equals(WHITE_ID)) {
                    hasWhite = true;
                    break loop;
                }
            }
        }

        return hasWhite;
    }

    public static List<Boolean> getBlackAfterWhiteListBoolean(int col, int row, GridPane grid) {
        List<Boolean> hasBlackAfterWhiteList = Arrays.asList(
                hasBlackAfterWhite(GridHelper.getNeighbors00(col, row, grid)),
                hasBlackAfterWhite(GridHelper.getNeighbors45(col, row, grid)),
                hasBlackAfterWhite(GridHelper.getNeighbors90(col, row, grid)),
                hasBlackAfterWhite(GridHelper.getNeighbors135(col, row, grid)),
                hasBlackAfterWhite(GridHelper.getNeighbors180(col, row, grid)),
                hasBlackAfterWhite(GridHelper.getNeighbors225(col, row, grid)),
                hasBlackAfterWhite(GridHelper.getNeighbors270(col, row, grid)),
                hasBlackAfterWhite(GridHelper.getNeighbors315(col, row, grid))
        );

        return hasBlackAfterWhiteList;
    }

    public static void flipToBlack(List<Node> nodeList, GridPane grid) {
        List<Node> whitePieces = new ArrayList<>();

        nodeList = nodeList.stream()
                .filter(el -> el.getId() != null)
                .collect(Collectors.toList());

        if (hasBlackAfterWhite(nodeList)) {
            for (Node node : nodeList) {
                if (node.getId().equals(WHITE_ID)) {
                    whitePieces.add(node);
                }
            }
        }
        for (Node white : whitePieces) {
            turnPieceBlack(grid, GridPane.getColumnIndex(white), GridPane.getRowIndex(white));
        }
    }


    public static long countBlacks(GridPane grid) {

        List<String> blacks = new ArrayList<>();

        for (Node piece : grid.getChildren()) {
            if (piece.getId() != null) {
                blacks.add(piece.getId());
            }
        }
        return blacks.stream()
                .filter(id -> id.equals(BLACK_ID))
                .count();
    }

    public static long countWhites(GridPane grid) {

        List<String> blacks = new ArrayList<>();

        for (Node piece : grid.getChildren()) {
            if (piece.getId() != null) {
                blacks.add(piece.getId());
            }
        }
        return blacks.stream()
                .filter(id -> id.equals(WHITE_ID))
                .count();
    }


}