/*
 * Michael Pu
 * TetrisGameAI - Game
 * ICS3U1 - Mr. Radulovic
 * December 23, 2017
 */

package frontend;

import backend.ControllerKeys;
import backend.GameController;
import backend.Updatable;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Game extends Application {

    private static final double WINDOW_PERCENTAGE_OF_SCREEN = 0.8;

    private long prevTime;

    private Stage primaryStage;
    private Scene scene;
    private ArrayList<Updatable> updateItems;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        startBasicGame();
    }

    public void startBasicGame() {
        updateItems = new ArrayList<>();

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double minDimension = Math.min(screenBounds.getHeight(), screenBounds.getWidth());

        double height = minDimension*WINDOW_PERCENTAGE_OF_SCREEN;
        double width = minDimension*WINDOW_PERCENTAGE_OF_SCREEN;

        GameWindow gameWindow = new GameWindow(height, width);
        updateItems.add(gameWindow);

        scene = new Scene(gameWindow);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Tetris Game");
        primaryStage.setResizable(false);
        primaryStage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double deltaTime = (now-prevTime)/1E9;
                onUpdate(deltaTime);
                prevTime = now;
            }
        };

        prevTime = System.nanoTime();
        timer.start();

        startPlayerGame(gameWindow);
    }

    public void startAIGame() {

    }


    public void startPlayerGame(GameWindow gameWindow) {
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyPressed) {
                KeyCode code = keyPressed.getCode();
                GameController gameController = gameWindow.getmGameController();
                //System.out.println(code);
                switch (code) {
                    case LEFT:
                        gameController.keyPressed(ControllerKeys.LEFT);
                        break;
                    case RIGHT:
                        gameController.keyPressed(ControllerKeys.RIGHT);
                        break;
                    case UP:
                        gameController.keyPressed(ControllerKeys.UP);
                        break;
                    case DOWN:
                        gameController.keyPressed(ControllerKeys.DOWN);
                        break;
                    case SPACE:
                        gameController.keyPressed(ControllerKeys.SPACE);
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
        for (Updatable updatable: updateItems) {
            updatable.update(deltaTime);
        }
    }
}
