/*
 * Michael Pu
 * TetrisGameAI - Game
 * ICS3U1 - Mr.Radulovic
 * January 20, 2018
 */

package frontend;

import java.util.ArrayList;

import ai.Organism;
import ai.Population;
import backend.GameMode;
import backend.Updatable;
import backend.Utilities;
import frontend.aifasttrain.AIFastTrainGameWindow;
import frontend.aiplay.AIPlayGameWindow;
import frontend.aitrain.AITrainGameWindow;
import frontend.aiwatch.AIWatchGameWindow;
import frontend.base.GameWindow;
import frontend.common.GameController;
import frontend.common.KeyboardControllerHandler;
import frontend.menu.Menu;
import frontend.menu.MenuScreen;
import frontend.player.PlayerGameWindow;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Entry point into game
 */
public class Game extends Application {

	/**
	 * Name of image file containing icon of application.
	 */
	private static final String ICON_IMAGE = "icon.png";

	/**
	 * Minimum percentage of the minimum dimension of the screen that will be taken
	 * up by the window.
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
	 * List of objects that will be updated every frame.
	 */
	private ArrayList<Updatable> updateItems;

	/**
	 * The current game window (root pane).
	 */
	private GameWindow gameWindow;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		// set icon for application
		primaryStage.getIcons().add(Utilities.getImage(ICON_IMAGE));
		showMenu();
	}

	/**
	 * Shows the window for the {@link MenuScreen} of the game.
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
				default:
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
	 * Shows the window for the game.
	 *
	 * @param gameMode
	 *            The {@link GameMode} that should be launched.
	 * @param menu
	 *            The {@link Menu} that called this method.
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
			while ((((minDimension * WINDOW_PERCENTAGE_OF_SCREEN) / 2) * 3) > (maxDimension
					* WINDOW_PERCENTAGE_OF_SCREEN)) {
				minDimension -= 10;
			}
			height = minDimension * WINDOW_PERCENTAGE_OF_SCREEN;
			width = ((minDimension / 2) * 3) * WINDOW_PERCENTAGE_OF_SCREEN;
			break;
		default:
			break;
		}

		// opens game window
		Population loadedPopulation;
		Organism loadedOrganism;
		boolean fastMode;

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
			fastMode = menu.ismFastMode();
			startAIWatch(loadedOrganism, height, width, fastMode);
			break;
		case AI_PLAY:
			loadedOrganism = (Organism) menu.getmLoadedObject();
			fastMode = menu.ismFastMode();
			startAIPlay(loadedOrganism, height, width, fastMode);
		default:
			break;
		}

		// set up window
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

	private void startAIFastTrain(Population population, double height, double width) {
		gameWindow = new AIFastTrainGameWindow(population, primaryStage, height, width);
		scene = new Scene(gameWindow);
	}

	private void startAITrain(Population population, double height, double width) {
		gameWindow = new AITrainGameWindow(population, primaryStage, height, width);
		scene = new Scene(gameWindow);
	}

	private void startAIWatch(Organism organism, double height, double width, boolean fastMode) {
		gameWindow = new AIWatchGameWindow(primaryStage, organism, height, width, fastMode);
		scene = new Scene(gameWindow);
	}

	private void startAIPlay(Organism organism, double height, double width, boolean fastMode) {
		gameWindow = new AIPlayGameWindow(primaryStage, organism, height, width, fastMode);
		scene = new Scene(gameWindow);

		GameController playerGameController = ((AIPlayGameWindow) gameWindow).getmRightGameController();
		GameController aiGameController = ((AIPlayGameWindow) gameWindow).getmLeftGameController();
		KeyboardControllerHandler keyboardControllerHandler = new KeyboardControllerHandler(playerGameController,
				aiGameController);
		scene.addEventFilter(KeyEvent.KEY_PRESSED, keyboardControllerHandler);
	}

	private void startPlayerGame(double height, double width) {

		gameWindow = new PlayerGameWindow(primaryStage, height, width);
		scene = new Scene(gameWindow);

		GameController playerGameController = ((PlayerGameWindow) gameWindow).getmGameController();
		KeyboardControllerHandler keyboardControllerHandler = new KeyboardControllerHandler(playerGameController, null);
		scene.addEventFilter(KeyEvent.KEY_PRESSED, keyboardControllerHandler);
	}

	public void onUpdate(double deltaTime) {
		for (Updatable updatable : updateItems) {
			updatable.update(deltaTime);
		}
	}
}
