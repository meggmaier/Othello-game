package com.example.othellogame;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.Arrays;
import java.util.List;


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

        String id = GridHelper.getIdByRowColumnIndex(col, row, grid);

        if (id != null) {
            System.out.println("Already taken");
            return false;
        }

        if (!hasAtLeastOneEnemyInRange(col, row, grid)) {
            System.out.println("No neigthbord as an enemy");
            return false;
        }


        System.out.println();
//        boolean isLegal = false;
//
//        ObservableList<Node> pieces = grid.getChildren();
//
//        Point2D mousePoint = new Point2D(mouseX, mouseY);
//
//        for(Node piece : pieces){
//            boolean isEmpty = piece.getId().equals(emptyID);
//            boolean isWhite = piece.getId().equals(WHITE_ID);
//            boolean isBlack = piece.getId().equals(BLACK_ID);
//
//                //checking if slot is empty
//            if(piece.contains(mousePoint) && isEmpty) {
//                    continue;
//            }
        //checking if slot is next to at least 1 white piece (all directories); if yes continue
        //if(){
        //    continue;
        ///}
        //checking if there is black piece right after white piece(s); if yes - move is legal
        //if(){
        //        isLegal = true;
        //}


        return true;
    }

    private static boolean hasAtLeastOneEnemyInRange(int col, int row, GridPane grid) {
        List<String> neighbordsIds = Arrays.asList(
                GridHelper.getIdByRowColumnIndex(col, row - 1, grid),
                GridHelper.getIdByRowColumnIndex(col + 1, row - 1, grid),
                GridHelper.getIdByRowColumnIndex(col + 1, row, grid),
                GridHelper.getIdByRowColumnIndex(col + 1, row + 1, grid),
                GridHelper.getIdByRowColumnIndex(col + 1, row, grid),
                GridHelper.getIdByRowColumnIndex(col - 1, row + 1, grid),
                GridHelper.getIdByRowColumnIndex(col - 1, row, grid),
                GridHelper.getIdByRowColumnIndex(col - 1, row - 1, grid)
        );

        return neighbordsIds.stream()
                .filter(id -> id != null)
                .anyMatch(id -> id.equals(WHITE_ID));
    }

}