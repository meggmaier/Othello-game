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

        if (!hasAtLeastOneWhiteInRange(col, row, grid)) {
            System.out.println("No neighbor as an enemy");
            return false;
        }


        if (hasAtLeastOneWhiteInRange(col, row, grid)) {
            return getBlackAfterWhiteListBoolean(col, row, grid).stream()
                    .anyMatch(hasBlack -> hasBlack);
        }

        return true;
    }

    private static boolean hasAtLeastOneWhiteInRange(int col, int row, GridPane grid) {
        List<Node> neighbors00 = GridHelper.getNeighbors00(col, row, grid);
        List<Node> neighbors45 = GridHelper.getNeighbors45(col, row, grid);
        List<Node> neighbors90 = GridHelper.getNeighbors90(col, row, grid);
        List<Node> neighbors135 = GridHelper.getNeighbors135(col, row, grid);
        List<Node> neighbors180 = GridHelper.getNeighbors180(col, row, grid);
        List<Node> neighbors225 = GridHelper.getNeighbors225(col, row, grid);
        List<Node> neighbors270 = GridHelper.getNeighbors270(col, row, grid);
        List<Node> neighbors315 = GridHelper.getNeighbors315(col, row, grid);

        List<String> neighborsIds = new ArrayList<>();

        if(!neighbors00.isEmpty()){
            neighborsIds.add(neighbors00.get(0).getId());
        }
        if(!neighbors45.isEmpty()){
            neighborsIds.add(neighbors45.get(0).getId());
        }
        if(!neighbors90.isEmpty()){
            neighborsIds.add(neighbors90.get(0).getId());
        }
        if(!neighbors135.isEmpty()){
            neighborsIds.add(neighbors135.get(0).getId());
        }
        if(!neighbors180.isEmpty()){
            neighborsIds.add(neighbors180.get(0).getId());
        }
        if(!neighbors225.isEmpty()){
            neighborsIds.add(neighbors225.get(0).getId());
        }
        if(!neighbors270.isEmpty()){
            neighborsIds.add(neighbors270.get(0).getId());
        }
        if(!neighbors315.isEmpty()){
            neighborsIds.add(neighbors315.get(0).getId());
        }

        return neighborsIds.stream()
                .filter(id -> id != null)
                .anyMatch(id -> id.equals(WHITE_ID));
    }


    public static boolean hasBlackAfterWhite(List<Node> idList) {


        boolean hasBlack = false;
        String firstPiece;

        if(idList.isEmpty()){
            return hasBlack;
        }

        else {
            firstPiece = idList.get(0).getId();


            if (firstPiece != null && firstPiece.equals(WHITE_ID)) {

                for (int i = 0; i < idList.size() - 1; i++) {
                    String nextPiece = idList.get(i + 1).getId();

                    if (nextPiece == null) {
                        return hasBlack;
                    }

                    if (nextPiece.equals(BLACK_ID)) {
                        hasBlack = true;
                    }
                }
            }
        }

        return hasBlack;
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




}