/*
 * Michael Pu
 * TetrisGameAI - Sidebar
 * ICS3U1 - Mr. Radulovic
 * December 24, 2017
 */

package frontend;

import backend.ControllerKeys;
import backend.GameController;
import backend.Updatable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public abstract class Sidebar extends VBox implements Updatable {

    protected ArrayList<Updatable> mUpdateItems;

    public Sidebar(GameArea gameArea, GameController gameController, double margins, double sideBarHeight, double sideBarWidth) {
        super(margins);
        setPadding(new Insets(margins));
        setAlignment(Pos.TOP_CENTER);
        setPrefHeight(sideBarHeight);
        setPrefWidth(sideBarWidth);
        mUpdateItems = new ArrayList<>();

        StatsBox statsBox = new StatsBox(gameArea);
        getChildren().add(statsBox);
        mUpdateItems.add(statsBox);
    }

    @Override
    public void update(double deltaTime) {
        for (Updatable updatable : mUpdateItems) {
            updatable.update(deltaTime);
        }
    }
}
