package com.example.othellogame;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.Objects;


public class PiecesService extends Parent {

    public ColorState colorState;
    public double column;
    public double row;

    public PiecesService(ColorState colorState, double column, double row) {
        this.colorState = colorState;
        this.column = column;
        this.row = row;
    }

    public ColorState setColorState(ColorState colorState){
        return colorState;
    }

    static ImageView createEmpty(){
        Image emptyBack = new Image("pieces/empty.png");
        ImageView transparent = new ImageView(emptyBack);
        transparent.setFitHeight(50);
        transparent.setFitWidth(50);

        return transparent;
    }

    static ImageView createWhite(){
        Image emptyBack = new Image("pieces/white.gif");
        ImageView white = new ImageView(emptyBack);
        white.setFitHeight(50);
        white.setFitWidth(50);

        return white;
    }
    static ImageView createBlack(){
        Image emptyBack = new Image("pieces/black.gif");
        ImageView black = new ImageView(emptyBack);
        black.setFitHeight(50);
        black.setFitWidth(50);

        return black;
    }


    static void turnPieceBlack(GridPane board, double col, double row){

        for (Node piece : board.getChildren()) {
            if (GridPane.getColumnIndex(piece) == col && GridPane.getRowIndex(piece) == row) {
                ImageView imageView = (ImageView)piece;
                imageView.setImage(new Image("pieces/black.gif"));
            }
        }
    }

    static void turnPieceWhite(GridPane board, double col, double row){

        for (Node piece : board.getChildren()) {
            if (GridPane.getColumnIndex(piece) == col && GridPane.getRowIndex(piece) == row) {
                ImageView imageView = (ImageView)piece;
                imageView.setImage(new Image("pieces/white.gif"));
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

}