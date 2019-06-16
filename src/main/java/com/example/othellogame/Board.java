package com.example.othellogame;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Board {

    public static final int ELEMENT_MEASUREMENT = 50;
    private Image rectangleBack = new Image("background/table.jpg");

    public Group createRectangle() {
        ImageView back = new ImageView(rectangleBack);
        Group rectangle = new Group();
        Rectangle r = new Rectangle(50, 50);
        r.setFill(Color.BLACK);
        back.setFitHeight(ELEMENT_MEASUREMENT);
        back.setFitWidth(ELEMENT_MEASUREMENT);
        back.setX(0);
        back.setY(0);
        rectangle.getChildren().add(r);
        rectangle.getChildren().add(back);
        return rectangle;
    }

    public VBox createBoard() {
        VBox board = new VBox();
        HBox row1 = new HBox();
        HBox row2 = new HBox();
        HBox row3 = new HBox();
        HBox row4 = new HBox();
        HBox row5 = new HBox();
        HBox row6 = new HBox();
        HBox row7 = new HBox();
        HBox row8 = new HBox();

        for (int i = 0; i < 8; i++) {
            row1.getChildren().add(createRectangle());
            row2.getChildren().add(createRectangle());
            row3.getChildren().add(createRectangle());
            row4.getChildren().add(createRectangle());
            row5.getChildren().add(createRectangle());
            row6.getChildren().add(createRectangle());
            row7.getChildren().add(createRectangle());
            row8.getChildren().add(createRectangle());
        }
        board.getChildren().addAll(row1, row2, row3, row4, row5, row6, row7, row8);
        return board;
    }
}
