/*
 * Michael Pu
 * TetrisGameAI - Sidebar
 * ICS3U1 - Mr. Radulovic
 * December 26, 2017
 */

package frontend.base;

import backend.GameBrain;
import backend.Updatable;
import frontend.common.GameArea;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public abstract class Sidebar extends VBox implements Updatable {

    protected ArrayList<Updatable> mUpdateItems;

    public Sidebar(double margins, double sideBarHeight, double sideBarWidth) {
        super(margins);
        setPadding(new Insets(margins));
        setAlignment(Pos.TOP_CENTER);
        setPrefHeight(sideBarHeight);
        setPrefWidth(sideBarWidth);
        mUpdateItems = new ArrayList<>();
    }

    @Override
    public void update(double deltaTime) {
        for (Updatable updatable : mUpdateItems) {
            updatable.update(deltaTime);
        }
    }
}
