package com.example.othellogame;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class WhitePiecesService {

    private static final String WHITE_ID = "01";
    private static final String BLACK_ID = "02";
    private static final Random RANDOM = new Random();

    static boolean isMoveLegal(int col, int row, GridPane grid) {


        if (col < 0 || col > 7) {
            System.out.println("Out of board");
            return false;
        }

        if (row < 0 || row > 7) {
            System.out.println("Out of board");
            return false;
        }

        String id = GridHelper.getIdByColumnRowIndex(col, row, grid).getId();

        if (id != null) {
            System.out.println("Already taken");
            return false;
        }

        if (!hasAtLeastOneBlackInRange(col, row, grid)) {
            System.out.println("No neighbor as an enemy");
            return false;
        }


        if (hasAtLeastOneBlackInRange(col, row, grid)) {
            return getWhiteAfterBlackListBoolean(col, row, grid).stream()
                    .anyMatch(hasBlack -> hasBlack);
        }

        return true;
    }

    public static boolean hasWhiteAfterBlack(List<Node> idList) {


        boolean hasWhite = false;
        String firstPiece;

        if(idList.isEmpty()){
            return hasWhite;
        }

        else {
             firstPiece = idList.get(0).getId();


            if (firstPiece != null && firstPiece.equals(BLACK_ID)) {

                for (int i = 0; i < idList.size() - 1; i++) {
                    String nextPiece = idList.get(i + 1).getId();

                    if (nextPiece == null) {
                        return hasWhite;
                    }

                    if (nextPiece.equals(WHITE_ID)) {
                        hasWhite = true;
                    }
                }
            }
        }
        return hasWhite;
    }

    private static boolean hasAtLeastOneBlackInRange(int col, int row, GridPane grid) {
        List<Node> neighbors00 = GridHelper.getNeighbors00(col, row, grid);
        List<Node> neighbors45 = GridHelper.getNeighbors45(col, row, grid);
        List<Node> neighbors90 = GridHelper.getNeighbors90(col, row, grid);
        List<Node> neighbors135 = GridHelper.getNeighbors135(col, row, grid);
        List<Node> neighbors180 = GridHelper.getNeighbors180(col, row, grid);
        List<Node> neighbors225 = GridHelper.getNeighbors225(col, row, grid);
        List<Node> neighbors270 = GridHelper.getNeighbors270(col, row, grid);
        List<Node> neighbors315 = GridHelper.getNeighbors315(col, row, grid);

        List<String> neighborsIds = new ArrayList<>();

        if(!neighbors00.isEmpty()){
        neighborsIds.add(neighbors00.get(0).getId());
        }
        if(!neighbors45.isEmpty()){
            neighborsIds.add(neighbors45.get(0).getId());
        }
        if(!neighbors90.isEmpty()){
            neighborsIds.add(neighbors90.get(0).getId());
        }
        if(!neighbors135.isEmpty()){
            neighborsIds.add(neighbors135.get(0).getId());
        }
        if(!neighbors180.isEmpty()){
            neighborsIds.add(neighbors180.get(0).getId());
        }
        if(!neighbors225.isEmpty()){
            neighborsIds.add(neighbors225.get(0).getId());
        }
        if(!neighbors270.isEmpty()){
            neighborsIds.add(neighbors270.get(0).getId());
        }
        if(!neighbors315.isEmpty()){
            neighborsIds.add(neighbors315.get(0).getId());
        }
        return neighborsIds.stream()
                .filter(id -> id != null)
                .anyMatch(id -> id.equals(BLACK_ID));
    }

    public static List<Boolean> getWhiteAfterBlackListBoolean(int col, int row, GridPane grid) {
        List<Boolean> hasWhiteAfterBlackList = Arrays.asList(
                hasWhiteAfterBlack(GridHelper.getNeighbors00(col, row, grid)),
                hasWhiteAfterBlack(GridHelper.getNeighbors45(col, row, grid)),
                hasWhiteAfterBlack(GridHelper.getNeighbors90(col, row, grid)),
                hasWhiteAfterBlack(GridHelper.getNeighbors135(col, row, grid)),
                hasWhiteAfterBlack(GridHelper.getNeighbors180(col, row, grid)),
                hasWhiteAfterBlack(GridHelper.getNeighbors225(col, row, grid)),
                hasWhiteAfterBlack(GridHelper.getNeighbors270(col, row, grid)),
                hasWhiteAfterBlack(GridHelper.getNeighbors315(col, row, grid))
        );

        return hasWhiteAfterBlackList;
    }

    public static void flipToWhite(List<Node> nodeList, GridPane grid) {
        List<Node> blackPieces = new ArrayList<>();

        nodeList = nodeList.stream()
                .filter(el -> el.getId() != null)
                .collect(Collectors.toList());

        if (hasWhiteAfterBlack(nodeList)) {
            for (Node node : nodeList) {
                if (node.getId().equals(BLACK_ID)) {
                    blackPieces.add(node);
                }
            }
        }
        for (Node white : blackPieces) {
            PiecesService.turnPieceWhite(grid, GridPane.getColumnIndex(white), GridPane.getRowIndex(white));
        }
    }

    public static long countWhites(GridPane grid) {

        List<String> blacks = new ArrayList<>();

        for (Node piece : grid.getChildren()) {
            if (piece.getId() != null) {
                blacks.add(piece.getId());
            }
        }
        return blacks.stream()
                .filter(id -> id.equals(WHITE_ID))
                .count();
    }

    public static Node calculateComputerMove(GridPane grid) {

        Node randomMove = null;
        //Filter fee fields
        List<Node> freeFields = grid.getChildren().stream()
                .filter(el -> el.getId() == null)
                .collect(Collectors.toList());

        //Filter the available fields
        List<Node> availableFields = freeFields.stream()
                .filter(f-> isMoveLegal(GridPane.getColumnIndex(f), GridPane.getRowIndex(f), grid))
                .collect(Collectors.toList());

        //pick random field
        if (availableFields.isEmpty()){
            PopUpWindow.whiteCannotMove();
        }
        if(availableFields.size()>1){
            randomMove = availableFields.get(RANDOM.nextInt(availableFields.size()-1));
        }
        if (availableFields.size() == 1) {
            randomMove = availableFields.get(0);
        }

        return randomMove;
    }
}
