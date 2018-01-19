/*
 * Michael Pu
 * TetrisGameAI - GameWindow
 * ICS3U1 - Mr. Radulovic
 * December 30, 2017
 */

package frontend.base;

import backend.GameMode;
import backend.GameProcessor;
import backend.Updatable;
import backend.Utilities;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;

/**
 * Abstract class that is the base for the window for all game modes that contains features that all game modes will
 * have.
 */
public abstract class GameWindow extends BorderPane implements Updatable {

    /**
     * The margins for elements in the window.
     */
    protected static final int DEFAULT_MARGINS = 15;

    /**
     * Colour for the outline of the game area.
     */
    protected static final Color GAME_OUTLINE_COLOUR = Color.grayRgb(83);

    /**
     * Colour for the background of the game area.
     */
    protected static final Color GAME_BACKGROUND_COLOUR = Color.grayRgb(23);

    /**
     * List of objects that need to be updated every frame.
     */
    protected ArrayList<Updatable> mUpdateItems;

    /**
     * Height of the canvas that the game will be drawn on.
     */
    protected double mCanvasHeight;

    /**
     * Width of the canvas that the game will be drawn on.
     */
    protected double mCanvasWidth;

    /**
     * Height of the game area where the game grid will be displayed.
     */
    protected double mGameAreaHeight;

    /**
     * Width of the game area where the game grid will be displayed.
     */
    protected double mGameAreaWidth;

    /**
     * Height of the sidebar.
     */
    protected double mSideBarHeight;

    /**
     * Width of the sidebar.
     */
    protected double mSideBarWidth;

    /**
     * The mode that the game is currently in.
     */
    protected GameMode mGameMode;

    /**
     * The title of the window.
     */
    protected String mWindowTitle;

    /**
     * MediaPlayer object that will be playing the background music.
     */
    protected MediaPlayer mBackgroundMusic;

    /**
     * The {@link GameProcessor} that will be processing the logic of the game.
     */
    protected GameProcessor mGameProcessor;

    /**
     * The stage that the game window will be displayed in.
     */
    private Stage mStage;

    /**
     * @param stage The stage that the game window will be displayed in.
     * @param height Height of the game window.
     * @param width Width of the game window.
     * @param gameMode Mode that the game window is in.
     */
    public GameWindow(Stage stage, double height, double width, GameMode gameMode) {

        // initialize member variables
        mUpdateItems = new ArrayList<>();
        mGameMode = gameMode;
        mStage = stage;
        mWindowTitle = "Game Window";

        // set up background music
        mBackgroundMusic = new MediaPlayer(new Media(Utilities.getResourceAsURLString(Utilities.AUDIO_BACKGROUND_MUSIC)));
        mBackgroundMusic.setCycleCount(MediaPlayer.INDEFINITE);

        // set up size
        setMaxHeight(height);
        setMaxWidth(width);

        // set up size of components in the screen
        mCanvasHeight = height - (2 * DEFAULT_MARGINS);
        mCanvasWidth = 1.0 * mCanvasHeight / 2.0;

        mGameAreaHeight = mCanvasHeight + (2 * DEFAULT_MARGINS);
        mGameAreaWidth = mCanvasWidth + (2 * DEFAULT_MARGINS);
    }

    /**
     * Calls the update method in each of the objects in the {@link #mUpdateItems} list.
     *
     * @param deltaTime The number of seconds that have passed since the last update.
     */
    public void update(double deltaTime) {
        for (Updatable updatable : mUpdateItems) {
            updatable.update(deltaTime);
        }
    }

    public GameMode getmGameMode() {
        return mGameMode;
    }

    public String getmWindowTitle() {
        return mWindowTitle;
    }

    public MediaPlayer getmBackgroundMusic() {
        return mBackgroundMusic;
    }

    public Stage getmStage() {
        return mStage;
    }

    public GameProcessor getmGameProcessor() {
        return mGameProcessor;
    }
}
