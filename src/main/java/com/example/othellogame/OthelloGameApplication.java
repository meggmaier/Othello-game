package com.example.othellogame;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
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

import java.util.List;
import java.util.stream.Collectors;


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
    private static boolean turn = false;

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
            whitesQuantity = WhitePiecesService.countWhites(grid);

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
                int columnClicked = (int) Math.floor(mouseX / 50);
                int rowClicked = (int) Math.floor(mouseY / 50);

                boolean isMoveLegalBlack = PiecesService.isMoveLegal(columnClicked, rowClicked, grid);
                Node randomComputerMove = WhitePiecesService.calculateComputerMove(grid);

                System.out.println("isMoveLegal " + isMoveLegalBlack);


                if (!turn && isMoveLegalBlack) {
                    playerMove(columnClicked, rowClicked, grid);
                } else {
                    PopUpWindow.moveNotPossible(randomComputerMove, grid);
                    turn = false;
                }
                if (turn) {
                    computerMove(randomComputerMove, grid);
                } else {
                    PopUpWindow.whiteCannotMove();
                    turn = false;
                }


                blacksQuantity = PiecesService.countBlacks(grid);
                whitesQuantity = WhitePiecesService.countWhites(grid);
                blacksCounter.setText("Blacks: " + blacksQuantity);
                whitesCounter.setText("Whites: " + whitesQuantity);

                long pointsSum = blacksQuantity + whitesQuantity;

                if (pointsSum == 64) {
                    PopUpWindow.matchResult(blacksQuantity, whitesQuantity, grid);
                }
                if (whitesQuantity > 1 && blacksQuantity == 0){
                    PopUpWindow.matchResult(blacksQuantity, whitesQuantity, grid);
                }
                if (blacksQuantity >1 && whitesQuantity == 0) {
                    PopUpWindow.matchResult(blacksQuantity, whitesQuantity, grid);
                }
            });



        primaryStage.setScene(scene);
        primaryStage.setTitle("Othello Game");

        primaryStage.show();
    }

    public static void playerMove(int columnClicked, int rowClicked, GridPane grid){
        PiecesService.turnPieceBlack(grid, columnClicked, rowClicked);
        PiecesService.flipToBlack(GridHelper.getNeighbors00(columnClicked, rowClicked, grid), grid);
        PiecesService.flipToBlack(GridHelper.getNeighbors45(columnClicked, rowClicked, grid), grid);
        PiecesService.flipToBlack(GridHelper.getNeighbors90(columnClicked, rowClicked, grid), grid);
        PiecesService.flipToBlack(GridHelper.getNeighbors135(columnClicked, rowClicked, grid), grid);
        PiecesService.flipToBlack(GridHelper.getNeighbors180(columnClicked, rowClicked, grid), grid);
        PiecesService.flipToBlack(GridHelper.getNeighbors225(columnClicked, rowClicked, grid), grid);
        PiecesService.flipToBlack(GridHelper.getNeighbors270(columnClicked, rowClicked, grid), grid);
        PiecesService.flipToBlack(GridHelper.getNeighbors315(columnClicked, rowClicked, grid), grid);
        turn = true;
    }

    public static void computerMove(Node computerMove, GridPane grid) {

        int columnComputer = GridPane.getColumnIndex(computerMove);
        int rowComputer = GridPane.getRowIndex(computerMove);

        PiecesService.turnPieceWhite(grid, columnComputer, rowComputer);
        WhitePiecesService.flipToWhite(GridHelper.getNeighbors00(columnComputer, rowComputer, grid), grid);
        WhitePiecesService.flipToWhite(GridHelper.getNeighbors45(columnComputer, rowComputer, grid), grid);
        WhitePiecesService.flipToWhite(GridHelper.getNeighbors90(columnComputer, rowComputer, grid), grid);
        WhitePiecesService.flipToWhite(GridHelper.getNeighbors135(columnComputer, rowComputer, grid), grid);
        WhitePiecesService.flipToWhite(GridHelper.getNeighbors180(columnComputer, rowComputer, grid), grid);
        WhitePiecesService.flipToWhite(GridHelper.getNeighbors225(columnComputer, rowComputer, grid), grid);
        WhitePiecesService.flipToWhite(GridHelper.getNeighbors270(columnComputer, rowComputer, grid), grid);
        WhitePiecesService.flipToWhite(GridHelper.getNeighbors315(columnComputer, rowComputer, grid), grid);
        turn = false;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
