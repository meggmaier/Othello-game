package com.example.othellogame;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GridHelper {

    public static List<Node> getNeighbors00(int col, int row, GridPane grid) {
        List<Node> neighbors00 = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            if (i == 0) {
                continue;
            }
            neighbors00.add(
                    getIdByColumnRowIndex(col, row - i, grid));
        }
        neighbors00 = neighbors00.stream()
                .filter(e -> e != null)
                .collect(Collectors.toList());
        return neighbors00;
    }

    public static List<Node> getNeighbors45(int col, int row, GridPane grid) {
        List<Node> neighbors45 = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            if (i == 0) {
                continue;
            }
            neighbors45.add(
                    getIdByColumnRowIndex(col + i, row - i, grid));
        }

        neighbors45 = neighbors45.stream()
                .filter(e -> e != null)
                .collect(Collectors.toList());
        return neighbors45;
    }

    public static List<Node> getNeighbors90(int col, int row, GridPane grid) {
        List<Node> neighbors90 = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            if (i == 0) {
                continue;
            }
            neighbors90.add(
                    getIdByColumnRowIndex(col + i, row, grid));
        }

        neighbors90 = neighbors90.stream()
                .filter(e -> e != null)
                .collect(Collectors.toList());
        return neighbors90;
    }

    public static List<Node> getNeighbors135(int col, int row, GridPane grid) {
        List<Node> neighbors135 = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            if (i == 0) {
                continue;
            }
            neighbors135.add(
                    getIdByColumnRowIndex(col + i, row + i, grid));
        }

        neighbors135 = neighbors135.stream()
                .filter(e->e!=null)
                .collect(Collectors.toList());
        return neighbors135;
    }

    public static List<Node> getNeighbors180(int col, int row, GridPane grid) {
        List<Node> neighbors180 = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            if (i == 0) {
                continue;
            }
            neighbors180.add(
                    getIdByColumnRowIndex(col, row + i, grid));
        }

        neighbors180 = neighbors180.stream()
                .filter(e->e!=null)
                .collect(Collectors.toList());
        return neighbors180;
    }

    public static List<Node> getNeighbors225(int col, int row, GridPane grid) {
        List<Node> neighbors225 = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            if (i == 0) {
                continue;
            }
            neighbors225.add(
                    getIdByColumnRowIndex(col - i, row + i, grid));
        }

        neighbors225 = neighbors225.stream()
                .filter(e->e!=null)
                .collect(Collectors.toList());

        return neighbors225;
    }

    public static List<Node> getNeighbors270(int col, int row, GridPane grid) {
        List<Node> neighbors270 = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            if (i == 0) {
                continue;
            }
            neighbors270.add(
                    getIdByColumnRowIndex(col - i, row, grid));
        }

        neighbors270 = neighbors270.stream()
                .filter(e->e!=null)
                .collect(Collectors.toList());
        return neighbors270;
    }

    public static List<Node> getNeighbors315(int col, int row, GridPane grid) {
        List<Node> neighbors315 = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            if (i == 0) {
                continue;
            }
            neighbors315.add(
                    getIdByColumnRowIndex(col - i, row - i, grid));
        }

        neighbors315 = neighbors315.stream()
                .filter(e->e!=null)
                .collect(Collectors.toList());
        return neighbors315;
    }

    public static Node getIdByColumnRowIndex(int column, int row, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> children = gridPane.getChildren();

        for (Node node : children) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }

}
