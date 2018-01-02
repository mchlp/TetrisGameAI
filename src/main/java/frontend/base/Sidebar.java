/*
 * Michael Pu
 * TetrisGameAI - Sidebar
 * ICS3U1 - Mr. Radulovic
 * December 29, 2017
 */

package frontend.base;

import backend.GameMode;
import backend.Updatable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public abstract class Sidebar extends VBox implements Updatable {

    protected ArrayList<Updatable> mUpdateItems;
    protected FlowPane mButtonBar;
    protected GameWindow mGameWindow;

    public Sidebar(GameWindow gameWindow, double margins, double sideBarHeight, double sideBarWidth) {
        super(margins);
        mGameWindow = gameWindow;
        setPadding(new Insets(margins));
        setAlignment(Pos.TOP_CENTER);
        setPrefHeight(sideBarHeight);
        setPrefWidth(sideBarWidth);
        mUpdateItems = new ArrayList<>();
    }

    protected void addButtonBar() {
        mButtonBar = new FlowPane(10, 10);
        mButtonBar.setAlignment(Pos.CENTER);
        getChildren().add(mButtonBar);

        Button mainMenuButton = new Button("Main Menu");
        mButtonBar.getChildren().add(mainMenuButton);

        mainMenuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mGameWindow.mGameMode = GameMode.MAIN_MENU;
                mGameWindow.getmBackgroundMusic().stop();
            }
        });
    }

    @Override
    public void update(double deltaTime) {
        for (Updatable updatable : mUpdateItems) {
            updatable.update(deltaTime);
        }
    }
}
