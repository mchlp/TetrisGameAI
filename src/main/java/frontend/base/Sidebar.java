/*
 * Michael Pu
 * TetrisGameAI - Sidebar
 * ICS3U1 - Mr.Radulovic
 * January 18, 2018
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

/**
 * Abstract class that is the base for the sidebar in all game modes containing elements that all game modes will have.
 */
public abstract class Sidebar extends VBox implements Updatable {

    /**
     * List of objects that need to be updated every frame.
     */
    protected ArrayList<Updatable> mUpdateItems;

    /**
     * The pane that the buttons in the sidebar will be added to, allowing the buttons to appear neatly in rows.
     */
    protected FlowPane mButtonBar;

    /**
     * The {@link GameWindow} that the sidebar is added to.
     */
    protected GameWindow mGameWindow;

    /**
     * @param gameWindow    The window that the sidebar will be displayed in.
     * @param margins       The margins for the elements in the sidebar.
     * @param sideBarHeight The height of the sidebar.
     * @param sideBarWidth  The width of the sidebar.
     */
    public Sidebar(GameWindow gameWindow, double margins, double sideBarHeight, double sideBarWidth) {
        super(margins);

        // initializes the member variables
        mGameWindow = gameWindow;
        mUpdateItems = new ArrayList<>();

        // set up the display properties of the sidebar
        setPadding(new Insets(margins));
        setAlignment(Pos.TOP_CENTER);
        setPrefHeight(sideBarHeight);
        setPrefWidth(sideBarWidth);
    }

    /**
     * Adds a button bar to the bottom of the sidebar.
     */
    protected void addButtonBar() {

        // set up the button bar
        mButtonBar = new FlowPane(10, 10);
        mButtonBar.setAlignment(Pos.CENTER);
        getChildren().add(mButtonBar);

        // add main menu button to button bar
        Button mainMenuButton = new Button("Main Menu");
        mButtonBar.getChildren().add(mainMenuButton);

        // action listener for main menu button
        mainMenuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mGameWindow.mGameMode = GameMode.MAIN_MENU;
                mGameWindow.getmBackgroundMusic().stop();
            }
        });
    }

    /**
     * Calls the update method in each of the objects in the {@link #mUpdateItems} list.
     *
     * @param deltaTime The number of seconds that have passed since the last update.
     */
    @Override
    public void update(double deltaTime) {
        for (Updatable updatable : mUpdateItems) {
            updatable.update(deltaTime);
        }
    }
}
