package com.example.othellogame;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class WhitePiecesService {

    private static final Random RANDOM = new Random();

    public static Node calculateComputerMove(GridPane grid, String currentPlayerID, String opponentID) {

        Node randomMove = null;

        List<Node> availableFields = grid.getChildren().stream()
                .filter(el -> el.getId() == null)
                .filter(f -> Validator.isMoveLegal(GridPane.getColumnIndex(f), GridPane.getRowIndex(f), grid, currentPlayerID, opponentID))
                .collect(Collectors.toList());

        if (availableFields.isEmpty()) {
            PopUpWindow.whiteCannotMove();
        }
        if(availableFields.size() > 1) {
            randomMove = availableFields.get(RANDOM.nextInt(availableFields.size()-1));
        }
        if (availableFields.size() == 1) {
            randomMove = availableFields.get(0);
        }

        return randomMove;
    }
}
