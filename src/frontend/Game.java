/*
 * Michael Pu
 * TetrisGameAI - Game
 * ICS3U1 - Mr. Radulovic
 * December 26, 2017
 */

package frontend;

import ai.Organism;
import backend.ControllerKeys;
import backend.Updatable;
import frontend.aiFastTrain.AIFastTrainGameWindow;
import frontend.aiTrain.AITrainGameWindow;
import frontend.aiWatch.AIWatchGameWindow;
import frontend.base.GameWindow;
import frontend.common.GameController;
import backend.GameMode;
import frontend.player.PlayerGameWindow;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import menu.Menu;
import menu.MenuScreen;

import java.util.ArrayList;

public class Game extends Application {

    private static final double WINDOW_PERCENTAGE_OF_SCREEN = 0.8;

    private long prevTime;
    private AnimationTimer menuTimer;
    private AnimationTimer gameTimer;

    private Stage primaryStage;
    private Scene scene;
    private ArrayList<Updatable> updateItems;
    private GameWindow gameWindow;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        //startGame();
        showMenu();
    }

    public void showMenu() {
        MenuScreen menuScreen = new MenuScreen(primaryStage);
        Scene menuScene = new Scene(menuScreen);
        primaryStage.setScene(menuScene);
        primaryStage.setResizable(false);
        primaryStage.show();

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

        menuTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (menuScreen.getmGameMode() != GameMode.MAIN_MENU) {
                    menuTimer.stop();
                    startGame(menuScreen.getmGameMode(), menuScreen.getmMenu());
                }
            }
        };
        menuTimer.start();
    }

    public void startGame(GameMode gameMode, Menu menu) {
        updateItems = new ArrayList<>();

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double minDimension = Math.min(screenBounds.getHeight(), screenBounds.getWidth());

        double height = minDimension * WINDOW_PERCENTAGE_OF_SCREEN;
        double width = minDimension * WINDOW_PERCENTAGE_OF_SCREEN;

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
                Organism loadedOrganism = (Organism) menu.getmLoadedObject();
                startAIWatch(loadedOrganism, height, width);
                break;
        }

        updateItems.add(gameWindow);
        primaryStage.setScene(scene);
        primaryStage.setTitle(gameWindow.getmWindowTitle());
        primaryStage.setResizable(false);
        primaryStage.show();

        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double deltaTime = (now - prevTime) / 1E9;
                onUpdate(deltaTime);
                prevTime = now;

                if (gameWindow.getmGameMode() == GameMode.MAIN_MENU) {
                    gameTimer.stop();
                    primaryStage.hide();
                    showMenu();
                }
            }
        };

        prevTime = System.nanoTime();
        gameTimer.start();
    }

    public void startAIFastTrain(double height, double width) {
        gameWindow = new AIFastTrainGameWindow(height, width);
        scene = new Scene(gameWindow);
    }

    public void startAITrain(double height, double width) {
        gameWindow = new AITrainGameWindow(height, width);
        scene = new Scene(gameWindow);
    }

    public void startAIWatch(Organism organism, double height, double width) {
        gameWindow = new AIWatchGameWindow(organism, height, width);
        scene = new Scene(gameWindow);
    }


    public void startPlayerGame(double height, double width) {

        gameWindow = new PlayerGameWindow(height, width);
        scene = new Scene(gameWindow);

        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyPressed) {
                KeyCode code = keyPressed.getCode();
                GameController gameController = ((PlayerGameWindow) gameWindow).getmGameController();
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
            System.out.println(1/deltaTime);
            updatable.update(deltaTime);
        }
    }
}
