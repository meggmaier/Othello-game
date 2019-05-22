package com.example.othellogame;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


@SpringBootApplication
public class OthelloGameApplication extends Application implements MouseListener {


    private Button newGameBtn = new Button("New Game");

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Group root = new Group();
        VBox board = new Board().createBoard();
        HBox lowBar = new HBox();
        VBox game = new VBox();
        GridPane grid = new GridPane();


        game.getChildren().addAll(board, lowBar);

        lowBar.getChildren().add(newGameBtn);
        newGameBtn.setOnAction((e) -> {PiecesService.setOnStartingPositions(grid);});

        root.getChildren().add(game);
        root.getChildren().add(grid);

        Scene scene = new Scene(root, 400, 483, Color.BLACK);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Othello Game");

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);

    }
}
