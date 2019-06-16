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

import static com.example.othellogame.PiecesService.BLACK_ID;
import static com.example.othellogame.PiecesService.WHITE_ID;
import static com.example.othellogame.Board.ELEMENT_MEASUREMENT;

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
    private static boolean computerTurn = false;

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
            showPoints();
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
            int columnClicked = (int) Math.floor(mouseX / ELEMENT_MEASUREMENT);
            int rowClicked = (int) Math.floor(mouseY / ELEMENT_MEASUREMENT);

            boolean isBlackMoveLegal = Validator.isMoveLegal(columnClicked, rowClicked, grid, BLACK_ID, WHITE_ID);
            System.out.println("isMoveLegal " + isBlackMoveLegal);

            if (!computerTurn && isBlackMoveLegal) {
                playerMove(columnClicked, rowClicked, grid);

            } else {
                Node randomComputerMove = WhitePiecesService.calculateComputerMove(grid, WHITE_ID, BLACK_ID);
                PopUpWindow.moveNotPossible(randomComputerMove, grid);
                computerTurn = false;
            }

            if (computerTurn) {
                Node randomComputerMove = WhitePiecesService.calculateComputerMove(grid, WHITE_ID, BLACK_ID);
                computerMove(randomComputerMove, grid);
            }

            showPoints();
            long pointsSum = blacksQuantity + whitesQuantity;

            if (pointsSum == 64) {
                PopUpWindow.matchResult(blacksQuantity, whitesQuantity, grid);
            }
            if (whitesQuantity > 1 && blacksQuantity == 0) {
                PopUpWindow.matchResult(blacksQuantity, whitesQuantity, grid);
            }
            if (blacksQuantity > 1 && whitesQuantity == 0) {
                PopUpWindow.matchResult(blacksQuantity, whitesQuantity, grid);
            }
        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("Othello Game");

        primaryStage.show();
    }

    public static void playerMove(int columnClicked, int rowClicked, GridPane grid) {
        PiecesService.turnPieceBlack(grid, columnClicked, rowClicked);
        List<List> allNeighbors = GridHelper.getAllNeighbors(columnClicked, rowClicked, grid);

        for (List<Node> neighbors : allNeighbors) {
            PiecesService.flipToColor(neighbors, grid, BLACK_ID, WHITE_ID);
        }
        computerTurn = true;
    }

    public static void computerMove(Node computerMove, GridPane grid) {

        int columnComputer = GridPane.getColumnIndex(computerMove);
        int rowComputer = GridPane.getRowIndex(computerMove);
        boolean isComputerMoveLegal = Validator.isMoveLegal(columnComputer, rowComputer, grid, WHITE_ID, BLACK_ID);

        if (isComputerMoveLegal) {
            PiecesService.turnPieceWhite(grid, columnComputer, rowComputer);
            List<List> allNeighbors = GridHelper.getAllNeighbors(columnComputer, rowComputer, grid);

            for (List<Node> neighbors : allNeighbors) {
                PiecesService.flipToColor(neighbors, grid, WHITE_ID, BLACK_ID);
            }
            computerTurn = false;
        }
        else {
            PopUpWindow.whiteCannotMove();
            computerTurn = false;
        }
    }
    public void showPoints(){
        blacksQuantity = PiecesService.countPieces(grid, BLACK_ID);
        whitesQuantity = PiecesService.countPieces(grid, WHITE_ID);

        blacksCounter.setText("Blacks: " + blacksQuantity);
        whitesCounter.setText("Whites: " + whitesQuantity);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
