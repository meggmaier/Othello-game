package com.example.othellogame;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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
    private long whitesQuantity;
    private long blacksQuantity;
    private Label whitesCounter = new Label("Whites: 0");
    private Label blacksCounter = new Label("Blacks: 0");

    @Override
    public void start(Stage primaryStage) throws Exception {

        game.getChildren().addAll(board, lowBar);

        lowBar.getChildren().add(whitesCounter);
        whitesCounter.setPrefWidth(150);
        whitesCounter.setFont(new Font("Arial", 24));
        whitesCounter.setTextFill(Color.WHITE);


        lowBar.getChildren().add(newGameBtn);
        newGameBtn.setOnAction((e) -> {
            int gridSize = grid.getChildren().size();
            if (gridSize > 0) {
                PiecesService.clearBoard(grid);
                PiecesService.setOnStartingPositions(grid);
            } else {
                PiecesService.setOnStartingPositions(grid);
            }
            blacksQuantity = PiecesService.countBlacks(grid);
            whitesQuantity = PiecesService.countWhites(grid);

            blacksCounter.setText("Blacks: " + blacksQuantity);
            whitesCounter.setText("Whites: " + whitesQuantity);
        });
        newGameBtn.setPrefWidth(100);

        lowBar.getChildren().add(blacksCounter);

        blacksCounter.setPrefWidth(150);
        blacksCounter.setFont(new Font("Arial", 24));
        blacksCounter.setTextFill(Color.WHITE);

        root.getChildren().add(game);
        root.getChildren().add(grid);

        Scene scene = new Scene(root, 400, 430, Color.BLACK);
        scene.setOnMouseClicked((event) -> {
            double mouseX = event.getX();
            double mouseY = event.getY();
            int columnClicked = (int)Math.floor(mouseX / 50);
            int rowClicked = (int)Math.floor(mouseY / 50);

            boolean isMoveLegal = PiecesService.isMoveLegal(columnClicked, rowClicked, grid);
            System.out.println("isMoveLegal "+isMoveLegal);

            if(isMoveLegal){
                PiecesService.turnPieceBlack(grid, columnClicked, rowClicked);
            }

            blacksQuantity = PiecesService.countBlacks(grid);
            whitesQuantity = PiecesService.countWhites(grid);
            blacksCounter.setText("Blacks: " + blacksQuantity);
            whitesCounter.setText("Whites: " + whitesQuantity);

        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("Othello Game");

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);


    }
}
