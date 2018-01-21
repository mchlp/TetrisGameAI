/*
 * Michael Pu
 * TetrisGameAI - AITrainGameWindow
 * ICS3U1 - Mr.Radulovic
 * January 20, 2018
 */

package frontend.aitrain;

import ai.Population;
import ai.GUITrainer;
import backend.GameMode;
import frontend.base.TwoPanelGameWindow;
import frontend.common.GameArea;
import frontend.common.GameController;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Game window for AI train mode with GUI.
 */
public class AITrainGameWindow extends TwoPanelGameWindow {

    private Population mPopulation;
    private GUITrainer mGUITrainer;
    private GameArea mGameArea;

    public AITrainGameWindow(Population population, Stage stage, double height, double width) {
        super(stage, height, width, GameMode.AI_TRAINER);

        mWindowTitle = "Tetris Game - AI Train Version";

        mPopulation = population;

        // set up left side of screen (game area)
        Rectangle gameAreaBackground = new Rectangle(mCanvasWidth + 10, mCanvasHeight + 10, GAME_OUTLINE_COLOUR);
        mGamePane.getChildren().add(gameAreaBackground);

        mGameArea = new GameArea(mBackgroundMusic, mCanvasWidth, mCanvasHeight, GAME_BACKGROUND_COLOUR, mGameMode);
        mGameProcessor = mGameArea.getmGameProcessor();
        mGamePane.getChildren().add(mGameArea);
        mGameController = new GameController(mGameArea);
        mUpdateItems.add(mGameArea);

        mGUITrainer = new GUITrainer(mGameArea.getmGameProcessor(), mGameController, mPopulation);
        mUpdateItems.add(mGUITrainer);

        // set up right side of screen (sidebar)
        AITrainSidebar aiTrainSidebar = new AITrainSidebar(this, mGameArea, mGUITrainer, DEFAULT_MARGINS, mSideBarHeight, mSideBarWidth);
        setRight(aiTrainSidebar);
        mUpdateItems.add(aiTrainSidebar);
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);

    }
}
