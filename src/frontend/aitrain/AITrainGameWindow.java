/*
 * Michael Pu
 * TetrisGameAI - AITrainGameWindow
 * ICS3U1 - Mr. Radulovic
 * December 30, 2017
 */

package frontend.aitrain;

import ai.Population;
import ai.Trainer;
import backend.GameMode;
import frontend.base.TwoPanelGameWindow;
import frontend.common.GameArea;
import frontend.common.GameController;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class AITrainGameWindow extends TwoPanelGameWindow {

    private Population mPopulation;
    private Trainer mTrainer;
    private GameArea mGameArea;

    public AITrainGameWindow(Population population, Stage stage, double height, double width) {
        super(stage, height, width, GameMode.AI_TRAINER);

        mWindowTitle = "Tetris Game - AI Train Version";

        mPopulation = population;

        Rectangle gameAreaBackground = new Rectangle(mCanvasWidth + 10, mCanvasHeight + 10, GAME_OUTLINE_COLOUR);
        mGamePane.getChildren().add(gameAreaBackground);

        mGameArea = new GameArea(mBackgroundMusic, mCanvasWidth, mCanvasHeight, GAME_BACKGROUND_COLOUR, mGameMode);
        mGamePane.getChildren().add(mGameArea);
        mGameController = new GameController(mGameArea);
        mUpdateItems.add(mGameArea);

        mTrainer = new Trainer(mGameArea.getmGameProcessor(), mGameController, mPopulation);
        mUpdateItems.add(mTrainer);

        AITrainSidebar aiTrainSidebar = new AITrainSidebar(this, mGameArea, mTrainer, DEFAULT_MARGINS, mSideBarHeight, mSideBarWidth);
        setRight(aiTrainSidebar);
        mUpdateItems.add(aiTrainSidebar);
    }
}
