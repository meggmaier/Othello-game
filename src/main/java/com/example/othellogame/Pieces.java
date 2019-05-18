package com.example.othellogame;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.HashMap;

public class Pieces extends Parent {

    private GridPane board = new GridPane();


    public Pieces(String color){

        GridPane piece = new GridPane();

        if(color == "blank"){
            piece.setVgap(4);
            piece.setHgap(4);
        }

        if(color == "white"){

            Image whiteBack = new Image("pieces/white.gif");
            ImageView white = new ImageView(whiteBack);
            white.setFitHeight(46);
            white.setFitWidth(46);

            piece.getChildren().add(white);
            piece.setHgap(4);
            piece.setVgap(4);
        }
        if(color == "black"){

            Image blackBack = new Image("pieces/black.gif");
            ImageView black = new ImageView(blackBack);
            black.setFitHeight(46);
            black.setFitWidth(46);

            piece.getChildren().add(black);
            piece.setHgap(4);
            piece.setVgap(4);
        }
    }



    public GridPane turnPieceBlack(int col, int row){

        for (Node piece : this.board.getChildren()) {
            if (GridPane.getColumnIndex(piece) == col && GridPane.getRowIndex(piece) == row) {
                board.add(new Pieces("black"),col,row);
            }
        }
        return board;
    }

    public GridPane turnPieceWhite(int col, int row){

          for (Node piece : this.board.getChildren()) {
              if (GridPane.getColumnIndex(piece) == col && GridPane.getRowIndex(piece) == row) {
                  board.add(new Pieces("white"),col,row);
              }
          }
          return board;
    }

    public GridPane piecesOnStartingPositions(){

        for(int i=0;i<8;i++){
            this.board.add(new Pieces("blank"), i, i);
        }

        turnPieceWhite(4,5);
        turnPieceWhite(5,4);

        turnPieceBlack(4,4);
        turnPieceBlack(5,5);

        return board;
    }



}
