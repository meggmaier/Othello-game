package com.example.othellogame;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.Objects;


public class PiecesService extends Parent {


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
                imageView.setId("02");

            }
        }

    }

    static void turnPieceWhite(GridPane board, double col, double row){

        for (Node piece : board.getChildren()) {
            if (GridPane.getColumnIndex(piece) == col && GridPane.getRowIndex(piece) == row) {
                ImageView imageView = (ImageView)piece;
                imageView.setImage(new Image("pieces/white.gif"));
                imageView.setId("01");
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

        grid.getChildren().remove(0,grid.getChildren().size());
    }

}