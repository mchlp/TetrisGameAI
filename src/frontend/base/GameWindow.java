/*
 * Michael Pu
 * TetrisGameAI - GameWindow
 * ICS3U1 - Mr.Radulovic
 * December 28, 2017
 */

package frontend.base;

import backend.Updatable;
import backend.Utilities;
import frontend.common.GameController;
import backend.GameMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
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
    protected GameController mGameController;
    protected StackPane mGamePane;
    protected GameMode mGameMode;
    protected String mWindowTitle;
    protected MediaPlayer mBackgroundMusic;
    private Stage mStage;

    public GameWindow(Stage stage, double height, double width, GameMode gameMode) {

        mUpdateItems = new ArrayList<>();
        mGameMode = gameMode;
        mStage = stage;
        mWindowTitle = "Game Window";

        mBackgroundMusic = new MediaPlayer(new Media(Utilities.getResourceAsURLString(Utilities.AUDIO_BACKGROUND_MUSIC)));
        mBackgroundMusic.setCycleCount(MediaPlayer.INDEFINITE);

        setPrefHeight(height);
        setPrefWidth(width);

        mCanvasHeight = height - (2 * DEFAULT_MARGINS);
        mCanvasWidth = 10.0 * mCanvasHeight / 20.0;

        mGameAreaHeight = mCanvasHeight + (2 * DEFAULT_MARGINS);
        mGameAreaWidth = mCanvasWidth + (2 * DEFAULT_MARGINS);

        mSideBarHeight = height;
        mSideBarWidth = width - mGameAreaWidth;

        mGamePane = new StackPane();
        mGamePane.setPrefHeight(mGameAreaHeight);
        mGamePane.setPrefWidth(mGameAreaWidth);
        setLeft(mGamePane);
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
