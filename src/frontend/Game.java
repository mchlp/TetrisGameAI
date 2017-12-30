/*
 * Michael Pu
 * TetrisGameAI - Game
 * ICS3U1 - Mr.Radulovic
 * December 28, 2017
 */

package frontend;

import ai.Organism;
import ai.Population;
import backend.GameMode;
import backend.Updatable;
import frontend.aifasttrain.AIFastTrainGameWindow;
import frontend.aiplay.AIPlayGameWindow;
import frontend.aitrain.AITrainGameWindow;
import frontend.aiwatch.AIWatchGameWindow;
import frontend.base.GameWindow;
import frontend.common.GameController;
import frontend.common.SinglePlayerControllerHandler;
import frontend.player.PlayerGameWindow;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import menu.Menu;
import menu.MenuScreen;

import java.util.ArrayList;

/**
 * Entry point into game
 */
public class Game extends Application {

    /**
     * Minimum percentage of the minimum dimension of the screen that will be taken up by the window
     */
    private static final double WINDOW_PERCENTAGE_OF_SCREEN = 0.8;

    // Variables for the animation timers
    private long prevTime;
    private AnimationTimer menuTimer;
    private AnimationTimer gameTimer;

    // Variables for the JavaFX GUI display
    private Stage primaryStage;
    private Scene scene;

    /**
     * List of objects that will be updated every frame
     */
    private ArrayList<Updatable> updateItems;

    /**
     * The current game window (root pane)
     */
    private GameWindow gameWindow;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        showMenu();
    }

    /**
     * Shows the window for the menu screen of the game
     */
    private void showMenu() {
        MenuScreen menuScreen = new MenuScreen(primaryStage);
        Scene menuScene = new Scene(menuScreen);
        primaryStage.setTitle("Tetris Game - Main Menu");
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

    /**
     * Shows the window for the actual game
     *
     * @param gameMode The {@link GameMode} that should be launched
     * @param menu     The {@link Menu} that called this method
     */
    private void startGame(GameMode gameMode, Menu menu) {
        updateItems = new ArrayList<>();

        // establish dimensions of screen
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenHeight = screenBounds.getHeight();
        double screenWidth = screenBounds.getWidth();

        double minDimension = Math.min(screenHeight, screenWidth);
        double maxDimension = Math.max(screenHeight, screenWidth);

        double height = minDimension;
        double width = minDimension;

        switch (gameMode) {
            case PLAYER:
            case AI_FAST_TRAINER:
            case AI_TRAINER:
            case AI_WATCHER:
                height = minDimension * WINDOW_PERCENTAGE_OF_SCREEN;
                width = minDimension * WINDOW_PERCENTAGE_OF_SCREEN;
                break;
            case AI_PLAY:
                while ((((minDimension * WINDOW_PERCENTAGE_OF_SCREEN) / 2) * 3) > (maxDimension * WINDOW_PERCENTAGE_OF_SCREEN)) {
                    minDimension -= 10;
                }
                height = minDimension * WINDOW_PERCENTAGE_OF_SCREEN;
                width = ((minDimension / 2) * 3) * WINDOW_PERCENTAGE_OF_SCREEN;
                break;
        }

        // opens game window
        Population loadedPopulation;
        Organism loadedOrganism;

        switch (gameMode) {
            case PLAYER:
                startPlayerGame(height, width);
                break;
            case AI_TRAINER:
                loadedPopulation = (Population) menu.getmLoadedObject();
                startAITrain(loadedPopulation, height, width);
                break;
            case AI_FAST_TRAINER:
                loadedPopulation = (Population) menu.getmLoadedObject();
                startAIFastTrain(loadedPopulation, height, width);
                break;
            case AI_WATCHER:
                loadedOrganism = (Organism) menu.getmLoadedObject();
                startAIWatch(loadedOrganism, height, width);
                break;
            case AI_PLAY:
                loadedOrganism = (Organism) menu.getmLoadedObject();
                startAIPlay(loadedOrganism, height, width);
        }

        updateItems.add(gameWindow);
        primaryStage.setScene(scene);
        primaryStage.setTitle(gameWindow.getmWindowTitle());
        primaryStage.setResizable(false);
        primaryStage.show();

        // set up animation timer for game
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

    /**
     * Starts a game in AI Fast Train mode
     *
     * @param population The {@link Population} to train
     * @param height     The height of the window
     * @param width      The width of the window
     */
    private void startAIFastTrain(Population population, double height, double width) {
        gameWindow = new AIFastTrainGameWindow(population, primaryStage, height, width);
        scene = new Scene(gameWindow);
    }

    private void startAITrain(Population population, double height, double width) {
        gameWindow = new AITrainGameWindow(population, primaryStage, height, width);
        scene = new Scene(gameWindow);
    }

    private void startAIWatch(Organism organism, double height, double width) {
        gameWindow = new AIWatchGameWindow(primaryStage, organism, height, width, false);
        scene = new Scene(gameWindow);
    }

    private void startAIPlay(Organism organism, double height, double width) {
        gameWindow = new AIPlayGameWindow(primaryStage, organism, height, width);
        scene = new Scene(gameWindow);

        GameController playerGameController = ((AIPlayGameWindow) gameWindow).getmRightGameController();
        GameController aiGameController = ((AIPlayGameWindow) gameWindow).getmLeftGameController();
        SinglePlayerControllerHandler singlePlayerControllerHandler = new SinglePlayerControllerHandler(playerGameController, aiGameController);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, singlePlayerControllerHandler);
    }


    private void startPlayerGame(double height, double width) {

        gameWindow = new PlayerGameWindow(primaryStage, height, width);
        scene = new Scene(gameWindow);

        GameController playerGameController = ((PlayerGameWindow) gameWindow).getmGameController();
        SinglePlayerControllerHandler singlePlayerControllerHandler = new SinglePlayerControllerHandler(playerGameController, null);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, singlePlayerControllerHandler);
    }

    private void onUpdate(double deltaTime) {
        for (Updatable updatable : updateItems) {
            //System.out.println(1/deltaTime);
            updatable.update(deltaTime);
        }
    }
}
