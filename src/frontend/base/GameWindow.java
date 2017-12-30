/*
 * Michael Pu
 * TetrisGameAI - GameWindow
 * ICS3U1 - Mr.Radulovic
 * December 28, 2017
 */

package frontend.base;

import backend.GameMode;
import backend.Updatable;
import backend.Utilities;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

public abstract class GameWindow extends BorderPane implements Updatable {

    protected static final int DEFAULT_MARGINS = 20;
    protected static final Color GAME_OUTLINE_COLOUR = Color.grayRgb(83);
    protected static final Color GAME_BACKGROUND_COLOUR = Color.grayRgb(23);

    protected ArrayList<Updatable> mUpdateItems;
    protected double mCanvasHeight;
    protected double mCanvasWidth;
    protected double mGameAreaHeight;
    protected double mGameAreaWidth;
    protected double mSideBarHeight;
    protected double mSideBarWidth;
    protected GameMode mGameMode;
    protected String mWindowTitle;
    protected MediaPlayer mBackgroundMusic;
    private Stage mStage;

    public GameWindow(Stage stage, double height, double width, GameMode gameMode) {

        // initialize member variables
        mUpdateItems = new ArrayList<>();
        mGameMode = gameMode;
        mStage = stage;
        mWindowTitle = "Game Window";

        // set up background music
        mBackgroundMusic = new MediaPlayer(new Media(Utilities.getResourceAsURLString(Utilities.AUDIO_BACKGROUND_MUSIC)));
        mBackgroundMusic.setCycleCount(MediaPlayer.INDEFINITE);

        // set up size of screen
        setPrefHeight(height);
        setPrefWidth(width);

        // set up size of components in the screen
        mCanvasHeight = height - (2 * DEFAULT_MARGINS);
        mCanvasWidth = 1.0 * mCanvasHeight / 2.0;

        mGameAreaHeight = mCanvasHeight + (2 * DEFAULT_MARGINS);
        mGameAreaWidth = mCanvasWidth + (2 * DEFAULT_MARGINS);
    }

    public GameMode getmGameMode() {
        return mGameMode;
    }

    public String getmWindowTitle() {
        return mWindowTitle;
    }

    public void update(double deltaTime) {
        for (Updatable updatable : mUpdateItems) {
            updatable.update(deltaTime);
        }
    }

    public MediaPlayer getmBackgroundMusic() {
        return mBackgroundMusic;
    }

    public Stage getmStage() {
        return mStage;
    }
}
