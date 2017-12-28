/*
 * Michael Pu
 * TetrisGameAI - Game
 * ICS3U1 - Mr. Radulovic
 * December 26, 2017
 */

package frontend;

import backend.ControllerKeys;
import backend.Updatable;
import frontend.aiFastTrain.AIFastTrainGameWindow;
import frontend.aiTrain.AITrainGameWindow;
import frontend.aiWatch.AIWatchGameWindow;
import frontend.common.GameController;
import backend.GameMode;
import frontend.player.PlayerGameWindow;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import menu.MenuScreen;

import java.util.ArrayList;

public class Game extends Application {

    private static final double WINDOW_PERCENTAGE_OF_SCREEN = 0.8;

    private long prevTime;
    private AnimationTimer mMenuTimer;
    private AnimationTimer mTimer;

    private Stage primaryStage;
    private Scene scene;
    private ArrayList<Updatable> updateItems;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        //startBasicGame();
        showMenu();
    }

    public void showMenu() {
        Stage menuDialog = new Stage();

        MenuScreen menuScreen = new MenuScreen();
        Scene menuScene = new Scene(menuScreen);
        menuDialog.setScene(menuScene);
        menuDialog.setResizable(false);
        menuDialog.show();

        menuScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyPressed) {
                switch (keyPressed.getCode()) {
                    case A:
                        menuScreen.toggleAdvanced();
                        break;
                }
            }
        });

        mMenuTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (menuScreen.getmGameMode() != GameMode.MAIN_MENU) {
                    menuDialog.hide();
                    mMenuTimer.stop();
                    startBasicGame(menuScreen.getmGameMode());
                }
            }
        };
        mMenuTimer.start();
    }

    public void startBasicGame(GameMode gameMode) {
        updateItems = new ArrayList<>();

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double minDimension = Math.min(screenBounds.getHeight(), screenBounds.getWidth());

        double height = minDimension * WINDOW_PERCENTAGE_OF_SCREEN;
        double width = minDimension * WINDOW_PERCENTAGE_OF_SCREEN;

        primaryStage.setResizable(false);
        primaryStage.show();

        mTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double deltaTime = (now - prevTime) / 1E9;
                onUpdate(deltaTime);
                prevTime = now;
            }
        };

        prevTime = System.nanoTime();
        mTimer.start();

        switch (gameMode) {
            case PLAYER:
                startPlayerGame(height, width);
                break;
            case AI_TRAINER:
                startAITrain(height, width);
                break;
            case AI_FAST_TRAINER:
                startAIFastTrain(height, width);
                break;
            case AI_WATCHER:
                startAIWatch(height, width);
                break;
        }
    }

    public void startAIFastTrain(double height, double width) {
        AIFastTrainGameWindow aiFastTrainGameWindow = new AIFastTrainGameWindow(height, width);
        updateItems.add(aiFastTrainGameWindow);
        scene = new Scene(aiFastTrainGameWindow);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Tetris Game - AI Fast Train Version");
    }

    public void startAITrain(double height, double width) {
        AITrainGameWindow aiTrainGameWindow = new AITrainGameWindow(height, width);
        updateItems.add(aiTrainGameWindow);
        scene = new Scene(aiTrainGameWindow);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Tetris Game - AI Train Version");
    }

    public void startAIWatch(double height, double width) {
        AIWatchGameWindow aiWatchGameWindow = new AIWatchGameWindow(height, width);
        updateItems.add(aiWatchGameWindow);
        scene = new Scene(aiWatchGameWindow);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Tetris Game - AI Watch Version");
    }


    public void startPlayerGame(double height, double width) {

        PlayerGameWindow playerGameWindow = new PlayerGameWindow(height, width);
        updateItems.add(playerGameWindow);
        scene = new Scene(playerGameWindow);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Tetris Game - Player Version");

        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyPressed) {
                KeyCode code = keyPressed.getCode();
                GameController gameController = playerGameWindow.getmGameController();
                //System.out.println(code);
                switch (code) {
                    case LEFT:
                        gameController.keyPressed(ControllerKeys.LEFT);
                        break;
                    case RIGHT:
                        gameController.keyPressed(ControllerKeys.RIGHT);
                        break;
                    case UP:
                        gameController.keyPressed(ControllerKeys.ROTATE);
                        break;
                    case DOWN:
                        gameController.keyPressed(ControllerKeys.DOWN);
                        break;
                    case SPACE:
                        gameController.keyPressed(ControllerKeys.DROP);
                        break;
                    case R:
                        gameController.keyPressed(ControllerKeys.RESTART);
                        break;
                    case P:
                        gameController.keyPressed(ControllerKeys.TOGGLE_PAUSE);
                        break;
                    case G:
                        gameController.keyPressed(ControllerKeys.TOGGLE_GRIDLINES);
                        break;
                    case ADD:
                        gameController.keyPressed(ControllerKeys.NEXT_LEVEL);
                }
                keyPressed.consume();
            }
        });
    }

    private void onUpdate(double deltaTime) {
        for (Updatable updatable : updateItems) {
            updatable.update(deltaTime);
        }
    }
}
