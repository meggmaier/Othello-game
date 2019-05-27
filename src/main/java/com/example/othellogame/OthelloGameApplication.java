package com.example.othellogame;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;






@SpringBootApplication
public class OthelloGameApplication extends Application {

    private Button newGameBtn = new Button("New Game");
    private Group root = new Group();
    private VBox board = new Board().createBoard();
    private HBox lowBar = new HBox();
    private VBox game = new VBox();
    private GridPane grid = new GridPane();

    @Override
    public void start(Stage primaryStage) throws Exception {

        game.getChildren().addAll(board, lowBar);

        lowBar.getChildren().add(newGameBtn);
        newGameBtn.setOnAction((e) -> {PiecesService.setOnStartingPositions(grid);});

        root.getChildren().add(game);
        root.getChildren().add(grid);

        Scene scene = new Scene(root, 400, 483, Color.BLACK);
        scene.setOnMouseClicked((event)-> {

                double mouseXcol = Math.floor(event.getX()/50);
                double mouseYrow = Math.floor(event.getY()/50);
                ImageView white = PiecesService.createWhite();
                ImageView black = PiecesService.createBlack();

                grid.getChildren().stream()
                        .flatMap(n -> )

                            PiecesService.turnPieceBlack(grid, mouseXcol, mouseYrow);


                        }
                    }


        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("Othello Game");

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);

    }
}
