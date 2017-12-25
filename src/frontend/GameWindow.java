/*
 * Michael Pu
 * TetrisGameAI - GameWindow
 * ICS3U1 - Mr. Radulovic
 * December 25, 2017
 */

package frontend;

import backend.GameController;
import backend.Updatable;
import frontend.player.PlayerSidebar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class GameWindow extends BorderPane implements Updatable {

    private static final int DEFAULT_MARGINS = 20;
    private static final Color GAME_OUTLINE_COLOUR =  Color.grayRgb(83);
    private static final Color GAME_BACKGROUND_COLOUR = Color.grayRgb(23);

    private GameController mGameController;
    private ArrayList<Updatable> mUpdateItems;

    public GameWindow(double height, double width) {

        super();
        mUpdateItems = new ArrayList<>();

        setPrefHeight(height);
        setPrefWidth(width);

        double canvasHeight = height-(2*DEFAULT_MARGINS);
        double canvasWidth = 10.0*canvasHeight/20.0;

        double gameAreaHeight = canvasHeight + (2*DEFAULT_MARGINS);
        double gameAreaWidth = canvasWidth + (2*DEFAULT_MARGINS);

        double sideBarHeight = height;
        double sideBarWidth = width - gameAreaWidth;


        StackPane gamePane = new StackPane();
        gamePane.setPrefHeight(gameAreaHeight);
        gamePane.setPrefWidth(gameAreaWidth);
        setLeft(gamePane);

        Rectangle gameAreaBackground = new Rectangle(canvasWidth+10, canvasHeight+10, GAME_OUTLINE_COLOUR);
        gamePane.getChildren().add(gameAreaBackground);

        GameArea gameArea = new GameArea(canvasWidth, canvasHeight, GAME_BACKGROUND_COLOUR);
        gamePane.getChildren().add(gameArea);
        mUpdateItems.add(gameArea);

        mGameController = new GameController(gameArea);

        PlayerSidebar sideBar = new PlayerSidebar(gameArea, mGameController, DEFAULT_MARGINS, sideBarHeight, sideBarWidth, GAME_BACKGROUND_COLOUR);
        setRight(sideBar);
        mUpdateItems.add(sideBar);

    }

    public GameController getmGameController() {
        return mGameController;
    }

    public void update(double deltaTime) {
        for (Updatable updatable: mUpdateItems) {
            updatable.update(deltaTime);
        }
    }
}
