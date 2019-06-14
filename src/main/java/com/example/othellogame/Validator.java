package com.example.othellogame;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Arrays;
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

        if (!hasAtLeastOneEnemyInRange(col, row, grid, currentPlayerID)) {
            System.out.println("No neighbor as an enemy");
            return false;
        }

        if (hasAtLeastOneEnemyInRange(col, row, grid, currentPlayerID)) {
            return hasPlayerPieceAfterOpponentsBoolean(col, row, grid, currentPlayerID, opponentID).stream()
                    .anyMatch(hasBlack -> hasBlack);
        }
        return true;
    }

    private static boolean hasAtLeastOneEnemyInRange(int col, int row, GridPane grid, String currentPlayerID) {

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
                .anyMatch(id -> id.equals(currentPlayerID));
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
        List<Boolean> hasPlayerPieceAfterOpponentsList = Arrays.asList(
                hasPlayerPieceAfterOpponents(GridHelper.getNeighbors00(col, row, grid), currentPlayerID, opponentID),
                hasPlayerPieceAfterOpponents(GridHelper.getNeighbors45(col, row, grid), currentPlayerID, opponentID),
                hasPlayerPieceAfterOpponents(GridHelper.getNeighbors90(col, row, grid), currentPlayerID, opponentID),
                hasPlayerPieceAfterOpponents(GridHelper.getNeighbors135(col, row, grid), currentPlayerID, opponentID),
                hasPlayerPieceAfterOpponents(GridHelper.getNeighbors180(col, row, grid), currentPlayerID, opponentID),
                hasPlayerPieceAfterOpponents(GridHelper.getNeighbors225(col, row, grid), currentPlayerID, opponentID),
                hasPlayerPieceAfterOpponents(GridHelper.getNeighbors270(col, row, grid), currentPlayerID, opponentID),
                hasPlayerPieceAfterOpponents(GridHelper.getNeighbors315(col, row, grid), currentPlayerID, opponentID)
        );

        return hasPlayerPieceAfterOpponentsList;
    }
}
