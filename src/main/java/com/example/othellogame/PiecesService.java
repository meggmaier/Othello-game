package com.example.othellogame;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import javafx.geometry.Point2D;


public class PiecesService extends Parent {

    private static String emptyID = null;
    private static String whiteID = "01";
    private static String blackID = "02";


    static ImageView createEmpty(){
        Image emptyBack = new Image("pieces/empty.png");
        ImageView transparent = new ImageView(emptyBack);
        transparent.setFitHeight(50);
        transparent.setFitWidth(50);


        return transparent;
    }


    static void turnPieceBlack(GridPane board, double col, double row){

        for (Node piece : board.getChildren()) {
            if (GridPane.getColumnIndex(piece) == col && GridPane.getRowIndex(piece) == row) {
                ImageView imageView = (ImageView)piece;
                imageView.setImage(new Image("pieces/black.gif"));
                imageView.setId(blackID);

            }
        }
    }

    static void turnPieceWhite(GridPane board, double col, double row){

        for (Node piece : board.getChildren()) {
            if (GridPane.getColumnIndex(piece) == col && GridPane.getRowIndex(piece) == row) {
                ImageView imageView = (ImageView)piece;
                imageView.setImage(new Image("pieces/white.gif"));
                imageView.setId(whiteID);
            }
        }

    }

    static void setOnStartingPositions(GridPane grid){

        for(int column=0; column<8; column++) {
            for (int row = 0; row < 8; row++) {
                grid.add(PiecesService.createEmpty(), column, row);
            }
        }
        turnPieceBlack(grid,4,3);
        turnPieceBlack(grid,3,4);
        turnPieceWhite(grid,3,3);
        turnPieceWhite(grid,4,4);

    }

    static void clearBoard(GridPane grid){
        int gridSize = grid.getChildren().size();

        grid.getChildren().remove(0,gridSize);
    }

    static boolean isMoveLegal(GridPane grid, double mouseX, double mouseY){

        boolean isLegal = false;
        ObservableList<Node> pieces = grid.getChildren();

        Point2D mousePoint = new Point2D(mouseX, mouseY);

        for(Node piece : pieces){
            boolean isEmpty = piece.getId().equals(emptyID);
            boolean isWhite = piece.getId().equals(whiteID);
            boolean isBlack = piece.getId().equals(blackID);

                //checking if slot is empty
            if(piece.contains(mousePoint) && isEmpty) {
                    continue;
            }
                //checking if slot is next to at least 1 white piece (all directories); if yes continue
            //if(){
                //    continue;
            ///}
                //checking if there is black piece right after white piece(s); if yes - move is legal
            //if(){
            //        isLegal = true;
            //}


            }

        return isLegal;
    }

}