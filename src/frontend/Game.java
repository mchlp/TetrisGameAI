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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.util.ArrayList;

public class Game extends Application {

    private static final int DEFAULT_MARGINS = 20;
    private static final double WINDOW_PERCENTAGE_OF_SCREEN = 0.8;

    private static final Color GAME_OUTLINE_COLOUR =  Color.grayRgb(83);
    private static final Color GAME_BACKGROUND_COLOUR = Color.grayRgb(23);

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

        Rectangle gameAreaBackground = new Rectangle(canvasWidth+10, canvasHeight+10, GAME_OUTLINE_COLOUR);
        gamePane.getChildren().add(gameAreaBackground);

        GameArea gameArea = new GameArea(canvasWidth, canvasHeight, GAME_BACKGROUND_COLOUR);
        gamePane.getChildren().add(gameArea);
        updateItems.add(gameArea);

        VBox sideBar = new VBox(DEFAULT_MARGINS);
        sideBar.setAlignment(Pos.TOP_CENTER);
        sideBar.setPrefHeight(sideBarHeight);
        sideBar.setPrefWidth(sideBarWidth);
        root.setRight(sideBar);

        sideBar.setPadding(new Insets(DEFAULT_MARGINS));

        NextTetrominoBox nextTetrominoBox = new NextTetrominoBox(gameArea, sideBarWidth/2, GAME_BACKGROUND_COLOUR);
        sideBar.getChildren().add(nextTetrominoBox);
        updateItems.add(nextTetrominoBox);

        StatsBox statsBox = new StatsBox(gameArea);
        sideBar.getChildren().add(statsBox);
        updateItems.add(statsBox);

        HBox buttonBar = new HBox(10);
        buttonBar.setAlignment(Pos.CENTER);
        sideBar.getChildren().add(buttonBar);

        Button restartButton = new Button("Restart");
        buttonBar.getChildren().add(restartButton);

        Button pauseButton = new Button("Toggle Pause");
        buttonBar.getChildren().add(pauseButton);

        Button gridlinesButton = new Button("Toggle Gridlines");
        buttonBar.getChildren().add(gridlinesButton);

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
                KeyCode code = keyPressed.getCode();
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
                }
                keyPressed.consume();
            }
        });

        restartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent click) {
                gameController.keyPressed(ControllerKeys.RESTART);
            }
        });

        pauseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent click) {
                gameController.keyPressed(ControllerKeys.TOGGLE_PAUSE);
            }
        });

        gridlinesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent click) {
                gameController.keyPressed(ControllerKeys.TOGGLE_GRIDLINES);
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
