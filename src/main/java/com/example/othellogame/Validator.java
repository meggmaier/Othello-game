package com.example.othellogame;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class Validator {

    static boolean isMoveLegal(int col, int row, GridPane grid, String currentPlayerID, String opponentID) {

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

        if (!hasAtLeastOneEnemyInRange(col, row, grid, opponentID)) {
            System.out.println("No neighbor as an enemy");
            return false;
        }

        if (hasAtLeastOneEnemyInRange(col, row, grid, opponentID)) {
            return hasPlayerPieceAfterOpponentsBoolean(col, row, grid, currentPlayerID, opponentID).stream()
                    .anyMatch(hasBlack -> hasBlack);
        }
        return true;
    }

    private static boolean hasAtLeastOneEnemyInRange(int col, int row, GridPane grid, String opponentID) {

        List<List> allNeighbors = GridHelper.getAllNeighbors(col, row, grid);
        List<String> neighborsIds = new ArrayList<>();

        for (List<Node> neighbors : allNeighbors) {
            if(!neighbors.isEmpty()) {
                neighborsIds.add(neighbors.get(0).getId());
            }
        }

        return neighborsIds.stream()
                .filter(id -> id != null)
                .anyMatch(id -> id.equals(opponentID));
    }

    public static boolean hasPlayerPieceAfterOpponents(List<Node> idList, String currentPlayerID, String opponentID) {

        boolean hasPlayerPiece = false;
        String firstPiece;

        if(idList.isEmpty()){
            return hasPlayerPiece;
        }
        else {
            firstPiece = idList.get(0).getId();

            if (firstPiece != null && firstPiece.equals(opponentID)) {

                for (int i = 0; i < idList.size() - 1; i++) {
                    String nextPiece = idList.get(i + 1).getId();

                    if (nextPiece == null) {
                        return hasPlayerPiece;
                    }

                    if (nextPiece.equals(currentPlayerID)) {
                        hasPlayerPiece = true;
                    }
                }
            }
        }
        return hasPlayerPiece;
    }

    public static List<Boolean> hasPlayerPieceAfterOpponentsBoolean(int col, int row, GridPane grid, String currentPlayerID, String opponentID) {

        List<Boolean> hasPlayerPieceAfterOpponentsList = new ArrayList<>();
        List<List> allNeighbors = GridHelper.getAllNeighbors(col, row, grid);

        for (List<Node> neighbors : allNeighbors) {
            hasPlayerPieceAfterOpponentsList.add(hasPlayerPieceAfterOpponents(neighbors, currentPlayerID, opponentID));
        }

        return hasPlayerPieceAfterOpponentsList;
    }
}
