/*
 * Michael Pu
 * TetrisGameAI - Game
 * ICS3U1 - Mr. Radulovic
 * December 23, 2017
 */

package frontend;

import backend.GameController;
import backend.ControllerKeys;
import backend.Updatable;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.util.ArrayList;

public class Game extends Application {

    private static final int DEFAULT_MARGINS = 20;
    private static final double WINDOW_PERCENTAGE_OF_SCREEN = 0.8;

    private long prevTime;

    private Stage primaryStage;
    private ArrayList<Updatable> updateItems;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        startGame();
    }

    public void startGame() {

        updateItems = new ArrayList<>();

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double minDimension = Math.min(screenBounds.getHeight(), screenBounds.getWidth());

        double height = minDimension*WINDOW_PERCENTAGE_OF_SCREEN;
        double width = minDimension*WINDOW_PERCENTAGE_OF_SCREEN;

        BorderPane root = new BorderPane();
        root.setPrefHeight(height);
        root.setPrefWidth(width);

        double canvasHeight = height-(2*DEFAULT_MARGINS);
        double canvasWidth = 10.0*canvasHeight/20.0;

        double gameAreaHeight = canvasHeight + (2*DEFAULT_MARGINS);
        double gameAreaWidth = canvasWidth + (2*DEFAULT_MARGINS);

        double sideBarHeight = height;
        double sideBarWidth = width - gameAreaWidth;


        StackPane gamePane = new StackPane();
        gamePane.setPrefHeight(gameAreaHeight);
        gamePane.setPrefWidth(gameAreaWidth);
        root.setLeft(gamePane);

        GameArea gameArea = new GameArea(canvasWidth, canvasHeight);
        gamePane.getChildren().add(gameArea);
        updateItems.add(gameArea);

        GraphicsContext gc = gameArea.getGraphicsContext2D();

        VBox sideBar = new VBox(10);
        sideBar.setAlignment(Pos.TOP_CENTER);
        sideBar.setPrefHeight(sideBarHeight);
        sideBar.setPrefWidth(sideBarWidth);
        root.setRight(sideBar);

        sideBar.setPadding(new Insets(DEFAULT_MARGINS));

        VBox statsBox = new VBox(10);
        sideBar.getChildren().add(statsBox);

        StatsBar scoreBar = new StatsBar("Score", "0");
        statsBox.getChildren().add(scoreBar);
        updateItems.add(scoreBar);

        StatsBar linesBar = new StatsBar("Lines", "0");
        statsBox.getChildren().add(linesBar);
        updateItems.add(linesBar);

        StatsBar levelBar = new StatsBar("Level", "0");
        statsBox.getChildren().add(levelBar);
        updateItems.add(levelBar);

        Button restart = new Button("Restart");
        sideBar.getChildren().add(restart);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
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

        GameController gameController = new GameController(gameArea);
        updateItems.add(gameController);

        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyPressed) {
                String code = keyPressed.getCode().toString();
                switch (code) {
                    case "LEFT":
                        gameController.setKeyPressed(ControllerKeys.LEFT, true);
                        break;
                    case "RIGHT":
                        gameController.setKeyPressed(ControllerKeys.RIGHT, true);
                        break;
                    case "UP":
                        gameController.setKeyPressed(ControllerKeys.UP, true);
                        break;
                    case "DOWN":
                        gameController.setKeyPressed(ControllerKeys.DOWN, true);
                        break;
                }
            }
        });

        scene.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyPressed) {
                String code = keyPressed.getCode().toString();
                switch (code) {
                    case "LEFT":
                        gameController.setKeyPressed(ControllerKeys.LEFT, false);
                        break;
                    case "RIGHT":
                        gameController.setKeyPressed(ControllerKeys.RIGHT, false);
                        break;
                    case "UP":
                        gameController.setKeyPressed(ControllerKeys.UP, false);
                        break;
                    case "DOWN":
                        gameController.setKeyPressed(ControllerKeys.DOWN, false);
                        break;
                }
            }
        });

        prevTime = System.nanoTime();
        timer.start();
    }

    private void onUpdate(double deltaTime) {
        for (Updatable updatable: updateItems) {
            updatable.update(deltaTime);
        }
    }
}
