package com.example.othellogame;

import ch.qos.logback.core.pattern.color.BlackCompositeConverter;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import jdk.nashorn.internal.ir.WhileNode;

import java.util.*;
import java.util.stream.Collectors;


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


        //while(hasAtLeastOneEnemyInRange(col, row, grid)) {
        //    if (!hasBlackAfterWhite(col, row, grid)) {
        //        System.out.println("No black piece following");
        //        return false;
        //    }
        //}
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
    private static boolean hasBlackAfterWhite(int col, int row, GridPane grid) {

        List<String> blackAfterWhiteIds00 = new ArrayList<>();
        List<String> blackAfterWhiteIds45 = new ArrayList<>();
        List<String> blackAfterWhiteIds90 = new ArrayList<>();
        List<String> blackAfterWhiteIds135 = new ArrayList<>();
        List<String> blackAfterWhiteIds180 = new ArrayList<>();
        List<String> blackAfterWhiteIds225 = new ArrayList<>();
        List<String> blackAfterWhiteIds270 = new ArrayList<>();
        List<String> blackAfterWhiteIds315 = new ArrayList<>();


        boolean hasBlackAfterWhite = false;

        for (int c = 0; c < 8; c++) {
            for (int r = 0; r < 8; r++) {
                if (c == 0 && r ==0){
                    continue;
                }
                blackAfterWhiteIds00 = Arrays.asList(
                        GridHelper.getIdByColumnRowIndex(col, row - r, grid));
                blackAfterWhiteIds45 = Arrays.asList(
                        GridHelper.getIdByColumnRowIndex(col + c, row - r, grid));
                blackAfterWhiteIds90 = Arrays.asList(
                        GridHelper.getIdByColumnRowIndex(col + c, row, grid));
                blackAfterWhiteIds135 = Arrays.asList(
                        GridHelper.getIdByColumnRowIndex(col + c, row + r, grid));
                blackAfterWhiteIds180 = Arrays.asList(
                        GridHelper.getIdByColumnRowIndex(col, row + c, grid));
                blackAfterWhiteIds225 = Arrays.asList(
                        GridHelper.getIdByColumnRowIndex(col - c, row + r, grid));
                blackAfterWhiteIds270 = Arrays.asList(
                        GridHelper.getIdByColumnRowIndex(col - c, row, grid));
                blackAfterWhiteIds315 = Arrays.asList(
                        GridHelper.getIdByColumnRowIndex(col - c, row - r, grid));
            }
        }

            for (int i=0; i<blackAfterWhiteIds00.size(); i++) {
                String nextPiece = blackAfterWhiteIds00.get(i+1);

                while (blackAfterWhiteIds00.get(0).equals(WHITE_ID)) {
                    if(nextPiece != null) {
                        if(nextPiece.equals(WHITE_ID)) {
                        continue;
                    }
                        hasBlackAfterWhite = nextPiece.equals(BLACK_ID);
                        break;
                    }
                }
            }

        return hasBlackAfterWhite;
    }

    public static long countWhites(GridPane grid){

        List<String> whites = new ArrayList<>();

        for (Node piece : grid.getChildren()){
            if(piece.getId() != null) {
                whites.add(piece.getId());
            }
        }
        long whitesQuantity = whites.stream()
                .filter(id -> id.equals(WHITE_ID))
                .count();

       return whitesQuantity;
    }

    public static long countBlacks(GridPane grid){

        List<String> blacks = new ArrayList<>();

        for (Node piece : grid.getChildren()){
            if(piece.getId() != null) {
                blacks.add(piece.getId());
            }
        }

        long blacksQuantity = blacks.stream()
                .filter(id -> id.equals(BLACK_ID))
                .count();

        return blacksQuantity;
    }

    }