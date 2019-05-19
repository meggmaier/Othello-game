package com.example.othellogame;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class OthelloGameApplication extends Application {


    //private Button newGame = new Button("New Game");
    //private Label blackPieces = new Label("Black Piece: ");
    //private Label whitePieces = new Label("WhitePieces: ");
    //private Label rowAHLabel = new Label ("A B C D E F G H");
    //private Label row18Label = new Label ("1 2 3 4 5 6 7 8");

    @Override
    public void start(Stage primaryStage) throws Exception {

        //prepare board
        Group root = new Group();
        VBox board = new Board().createBoard();

        GridPane grid = new GridPane();

        root.getChildren().add(board);
        root.getChildren().add(grid);

        Scene scene = new Scene(root, 413, 483, Color.BLACK);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Othello Game");

        //prepare elements
        for(int column=0; column<10; column++){
            for(int row=0;row<10;row++){
                grid.add(PiecesService.createEmpty(), column, row);
            }
        }

        //validate if it's working
        PiecesService.turnPieceBlack(grid, 1, 2);
        PiecesService.turnPieceWhite(grid, 1, 2);

        PiecesService.turnPieceBlack(grid, 5, 2);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);

    }
}
