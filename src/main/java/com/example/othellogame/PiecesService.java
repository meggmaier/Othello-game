package com.example.othellogame;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;


public class PiecesService extends Parent {

    static ImageView createWhite(){
        Image whiteBack = new Image("pieces/white.gif");
        ImageView white = new ImageView(whiteBack);
        white.setFitHeight(50);
        white.setFitWidth(50);

        return white;
    }

    static ImageView createBlack(){
        Image blackBack = new Image("pieces/black.gif");
        ImageView black = new ImageView(blackBack);
        black.setFitHeight(50);
        black.setFitWidth(50);

        return black;
    }

    static ImageView createEmpty(){
        Image emptyBack = new Image("pieces/empty.png");
        ImageView transparent = new ImageView(emptyBack);
        transparent.setFitHeight(50);
        transparent.setFitWidth(50);

        return transparent;
    }


    static void turnPieceBlack(GridPane board, int col, int row){

        for (Node piece : board.getChildren()) {
            if (GridPane.getColumnIndex(piece) == col && GridPane.getRowIndex(piece) == row) {
                ImageView imageView = (ImageView)piece;
                imageView.setImage(new Image("pieces/black.gif"));
            }
        }
    }

    static void turnPieceWhite(GridPane board, int col, int row){

          for (Node piece : board.getChildren()) {
              if (GridPane.getColumnIndex(piece) == col && GridPane.getRowIndex(piece) == row) {
                  ImageView imageView = (ImageView)piece;
                  imageView.setImage(new Image("pieces/white.gif"));
              }
          }
    }
}
