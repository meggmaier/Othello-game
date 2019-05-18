package com.example.othellogame;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class OthelloGameApplication extends Application {


    //private Button newGame = new Button("New Game");
    //private Label blackPieces = new Label("Black Pieces: ");
    //private Label whitePieces = new Label("WhitePieces: ");
    //private Label rowAHLabel = new Label ("A B C D E F G H");
    //private Label row18Label = new Label ("1 2 3 4 5 6 7 8");

       @Override
    public void start(Stage primaryStage) throws Exception {

        Group root = new Group();
        VBox board = new Board().createBoard();
        GridPane piecesOnBoard = new Pieces("x").piecesOnStartingPositions();


        root.getChildren().addAll(board, piecesOnBoard);

        Scene scene = new Scene(root, 413, 483, Color.BLACK);


        primaryStage.setScene(scene);
        primaryStage.setTitle("Othello Game");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);

    }
}
