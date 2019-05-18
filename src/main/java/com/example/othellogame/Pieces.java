package com.example.othellogame;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;


public class Pieces extends Parent {

    public Pieces(String color){

        if(color.equals("blank")){
            GridPane blankPiece = new GridPane();
        }

        if(color.equals("white")){

            GridPane whitePiece = new GridPane();

            Image whiteBack = new Image("pieces/white.gif");
            ImageView white = new ImageView(whiteBack);
            white.setFitHeight(46);
            white.setFitWidth(46);

            whitePiece.getChildren().add(white);
            whitePiece.setHgap(4);
            whitePiece.setVgap(4);
        }
        if(color.equals("black")){

            GridPane blackPiece = new GridPane();

            Image blackBack = new Image("pieces/black.gif");
            ImageView black = new ImageView(blackBack);
            black.setFitHeight(46);
            black.setFitWidth(46);

            blackPiece.getChildren().add(black);
            blackPiece.setHgap(4);
            blackPiece.setVgap(4);
        }
    }



    public void turnPieceBlack(GridPane board, int col, int row){

        for (Node piece : board.getChildren()) {
            if (GridPane.getColumnIndex(piece) == col && GridPane.getRowIndex(piece) == row) {
                board.getChildren().remove(piece);
                board.add(new Pieces("black"),col,row);
            }
        }

    }

    public void turnPieceWhite(GridPane board, int col, int row){

          for (Node piece : board.getChildren()) {
              if (GridPane.getColumnIndex(piece) == col && GridPane.getRowIndex(piece) == row) {
                  board.getChildren().remove(piece);
                  board.add(new Pieces("white"),col,row);
              }
          }

    }

    public GridPane piecesOnStartingPositions(){
        GridPane board = new GridPane();

        for(int i=0;i<8;i++){
            board.add(new Pieces("blank"), i, i);
        }

        turnPieceWhite(board, 4,5);
        turnPieceWhite(board, 5,4);

        turnPieceBlack(board, 4,4);
        turnPieceBlack(board,5,5);


        return board;
    }



}
