package com.example.othellogame;

import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.*;

import static com.example.othellogame.OthelloGameApplication.computerMove;

public class PopUpWindow {

    public static void moveNotPossible(Node computerMove, GridPane grid){

        Stage popUpWindow = new Stage();

        popUpWindow.initModality(Modality.APPLICATION_MODAL);

        Label label = new Label("This move is not possible");

        Button passButton = new Button("PASS");
        Button tryAgainButton = new Button("Try again");

        passButton.setOnAction(e -> {
            computerMove(computerMove, grid);
            popUpWindow.close();
        });
        passButton.setPrefWidth(75);

        tryAgainButton.setOnAction(e -> {
            popUpWindow.close();
        });
        tryAgainButton.setPrefWidth(75);

        VBox layout= new VBox(10);
        HBox buttons = new HBox();

        buttons.getChildren().addAll(passButton, tryAgainButton);
        layout.getChildren().addAll(label, buttons);

        layout.setAlignment(Pos.CENTER);

        Scene scene1= new Scene(layout, 150, 50);

        popUpWindow.setScene(scene1);
        popUpWindow.showAndWait();
    }
    public static void whiteCannotMove(){

        Stage popUpWindow = new Stage();

        popUpWindow.initModality(Modality.APPLICATION_MODAL);

        Label label = new Label("Your opponent cannot make a move");

        Button playerTurn = new Button("Your turn again");
        playerTurn.setOnAction(e -> {
            popUpWindow.close();
        });


        VBox layout= new VBox(10);

        layout.getChildren().addAll(label, playerTurn);

        layout.setAlignment(Pos.CENTER);

        Scene scene1= new Scene(layout, 350, 50);

        popUpWindow.setScene(scene1);
        popUpWindow.showAndWait();
    }

    public static void matchResult(long black, long white, GridPane grid){

        Stage popUpWindow = new Stage();

        popUpWindow.initModality(Modality.APPLICATION_MODAL);

        Label matchResult = new Label();
        Button congrats = new Button();
        VBox layout = new VBox();

        layout.getChildren().addAll(matchResult, congrats);

        Scene scene1= new Scene(layout, 150, 50);

        popUpWindow.setScene(scene1);
        popUpWindow.showAndWait();

        //if player win
        if (black>white || white == 0){
            matchResult.setText("You win!!!");
            matchResult.setFont(new Font("Arial", 16));
            congrats.setText("Congratulations!");
            congrats.setAlignment(Pos.CENTER);
            congrats.setOnAction(e->{
                PiecesService.clearBoard(grid);
                popUpWindow.close();
            });
        }
        //if player loose
        if(white>black || black == 0){
            matchResult.setText("You loose");
            matchResult.setFont(new Font("Arial", 16));
            congrats.setText("Try again?");
            congrats.setAlignment(Pos.CENTER);
            congrats.setOnAction(e->{
                PiecesService.clearBoard(grid);
                popUpWindow.close();
            });
        }
        //draw
        if(white == black){
            matchResult.setText("Draw");
            matchResult.setFont(new Font("Arial", 16));
            congrats.setText("Try again?");
            congrats.setAlignment(Pos.CENTER);
            congrats.setOnAction(e->{
                PiecesService.clearBoard(grid);
                popUpWindow.close();
            });


        }


    }

}

