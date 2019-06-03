package com.example.othellogame;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.*;


public class PiecesService extends Parent {

    private static final String WHITE_ID = "01";
    private static final String BLACK_ID = "02";


    static ImageView createEmpty() {
        Image emptyBack = new Image("pieces/empty.png");
        ImageView transparent = new ImageView(emptyBack);
        transparent.setFitHeight(50);
        transparent.setFitWidth(50);


        return transparent;
    }


    static void turnPieceBlack(GridPane board, double col, double row) {

        for (Node piece : board.getChildren()) {
            if (GridPane.getColumnIndex(piece) == col && GridPane.getRowIndex(piece) == row) {
                ImageView imageView = (ImageView) piece;
                imageView.setImage(new Image("pieces/black.gif"));
                imageView.setId(BLACK_ID);

            }
        }
    }

    static void turnPieceWhite(GridPane board, double col, double row) {

        for (Node piece : board.getChildren()) {
            if (GridPane.getColumnIndex(piece) == col && GridPane.getRowIndex(piece) == row) {
                ImageView imageView = (ImageView) piece;
                imageView.setImage(new Image("pieces/white.gif"));
                imageView.setId(WHITE_ID);
            }
        }

    }

    static void setOnStartingPositions(GridPane grid) {

        for (int column = 0; column < 8; column++) {
            for (int row = 0; row < 8; row++) {
                grid.add(PiecesService.createEmpty(), column, row);
            }
        }
        turnPieceBlack(grid, 4, 3);
        turnPieceBlack(grid, 3, 4);
        turnPieceWhite(grid, 3, 3);
        turnPieceWhite(grid, 4, 4);

    }

    static void clearBoard(GridPane grid) {
        int gridSize = grid.getChildren().size();

        grid.getChildren().remove(0, gridSize);
    }

    static boolean isMoveLegal(int col, int row, GridPane grid) {


        if (col < 0 || col > 7) {
            System.out.println("Out of board");
            return false;
        }

        if (row < 0 || row > 7) {
            System.out.println("Out of board");
            return false;
        }

        String id = GridHelper.getIdByColumnRowIndex(col, row, grid);

        if (id != null) {
            System.out.println("Already taken");
            return false;
        }

        if (!hasAtLeastOneEnemyInRange(col, row, grid)) {
            System.out.println("No neighbor as an enemy");
            return false;
        }

        List<String> blackAfterWhiteIds00 = new ArrayList<>();
        List<String> blackAfterWhiteIds45 = new ArrayList<>();
        List<String> blackAfterWhiteIds90 = new ArrayList<>();
        List<String> blackAfterWhiteIds135 = new ArrayList<>();
        List<String> blackAfterWhiteIds180 = new ArrayList<>();
        List<String> blackAfterWhiteIds225 = new ArrayList<>();
        List<String> blackAfterWhiteIds270 = new ArrayList<>();
        List<String> blackAfterWhiteIds315 = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
                if (i == 0){
                    continue;
                }
                blackAfterWhiteIds00.add(
                        GridHelper.getIdByColumnRowIndex(col, row - i, grid));
                blackAfterWhiteIds45.add(
                        GridHelper.getIdByColumnRowIndex(col + i, row - i, grid));
                blackAfterWhiteIds90.add(
                        GridHelper.getIdByColumnRowIndex(col + i, row, grid));
                blackAfterWhiteIds135.add(
                        GridHelper.getIdByColumnRowIndex(col + i, row + i, grid));
                blackAfterWhiteIds180.add(
                        GridHelper.getIdByColumnRowIndex(col, row + i, grid));
                blackAfterWhiteIds225.add(
                        GridHelper.getIdByColumnRowIndex(col - i, row + i, grid));
                blackAfterWhiteIds270.add(
                        GridHelper.getIdByColumnRowIndex(col - i, row, grid));
                blackAfterWhiteIds315.add(
                        GridHelper.getIdByColumnRowIndex(col - i, row - i, grid));
            }


            if (!hasBlackAfterWhite(blackAfterWhiteIds00)
            || !hasBlackAfterWhite(blackAfterWhiteIds45)
            || !hasBlackAfterWhite(blackAfterWhiteIds90)
            || !hasBlackAfterWhite(blackAfterWhiteIds135)
            || !hasBlackAfterWhite(blackAfterWhiteIds180)
            || !hasBlackAfterWhite(blackAfterWhiteIds225)
            || !hasBlackAfterWhite(blackAfterWhiteIds270)
            || !hasBlackAfterWhite(blackAfterWhiteIds315)
            ) {
                System.out.println("00: " + blackAfterWhiteIds00);
                System.out.println("45: " + blackAfterWhiteIds45);
                System.out.println("90: " + blackAfterWhiteIds90);
                System.out.println("135: " + blackAfterWhiteIds135);
                System.out.println("180: " + blackAfterWhiteIds180);
                System.out.println("225: " + blackAfterWhiteIds225);
                System.out.println("270: " + blackAfterWhiteIds270);
                System.out.println("315: " + blackAfterWhiteIds315);

                System.out.println("No black piece following");
                return false;
            }

        return true;
    }

    private static boolean hasAtLeastOneEnemyInRange(int col, int row, GridPane grid) {
        List<String> neighborsIds = Arrays.asList(
                GridHelper.getIdByColumnRowIndex(col, row - 1, grid),
                GridHelper.getIdByColumnRowIndex(col + 1, row - 1, grid),
                GridHelper.getIdByColumnRowIndex(col + 1, row, grid),
                GridHelper.getIdByColumnRowIndex(col + 1, row + 1, grid),
                GridHelper.getIdByColumnRowIndex(col, row + 1, grid),
                GridHelper.getIdByColumnRowIndex(col - 1, row + 1, grid),
                GridHelper.getIdByColumnRowIndex(col - 1, row, grid),
                GridHelper.getIdByColumnRowIndex(col - 1, row - 1, grid)
        );

        return neighborsIds.stream()
                .filter(id -> id != null)
                .anyMatch(id -> id.equals(WHITE_ID));
    }


    private static boolean hasBlackAfterWhite(List<String> idList){

            boolean hasBlack = false;
            String firstPiece = idList.get(0);


            while (firstPiece != null && firstPiece.equals(WHITE_ID)) {

                    int i = 0;
                    String nextPiece = idList.get(i + 1);

                    if (nextPiece == null) {
                        hasBlack = false;
                        break;
                    }
                    if (nextPiece.equals(BLACK_ID)) {
                        hasBlack = true;
                        break;
                    }
                    if (nextPiece.equals(WHITE_ID)){
                        i++;
                }
            }
            return hasBlack;
    }

    public static long countWhites(GridPane grid){

        List<String> whites = new ArrayList<>();

        for (Node piece : grid.getChildren()){
            if(piece.getId() != null) {
                whites.add(piece.getId());
            }
        }
        return whites.stream()
                .filter(id -> id.equals(WHITE_ID))
                .count();
    }

    public static long countBlacks(GridPane grid){

        List<String> blacks = new ArrayList<>();

        for (Node piece : grid.getChildren()){
            if(piece.getId() != null) {
                blacks.add(piece.getId());
            }
        }
        return blacks.stream()
                .filter(id -> id.equals(BLACK_ID))
                .count();
    }



    }