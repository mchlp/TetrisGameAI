/*
 * Michael Pu
 * TetrisGameAI - AITrainGameWindow
 * ICS3U1 - Mr. Radulovic
 * December 25, 2017
 */

package frontend.aiTrain;

import ai.Population;
import ai.Trainer;
import frontend.base.GameWindow;
import frontend.common.GameArea;
import frontend.common.GameController;
import frontend.common.GameMode;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.io.IOException;

public class AITrainGameWindow extends GameWindow {

    private Population mPopulation;
    private Trainer mTrainer;
    private GameArea mGameArea;

    public AITrainGameWindow(double height, double width) {
        super(height, width, GameMode.AI_TRAINER);

        File saveFile = new File("/home/mpu/Desktop/testAI.ser");
        //mPopulation = new Population(saveFile);
        mPopulation = Population.loadPopulationFromFile(new File("/home/mpu/Desktop/fastTestAI.ser"));

        Rectangle gameAreaBackground = new Rectangle(mCanvasWidth + 10, mCanvasHeight + 10, GAME_OUTLINE_COLOUR);
        mGamePane.getChildren().add(gameAreaBackground);

        mGameArea = new GameArea(mCanvasWidth, mCanvasHeight, GAME_BACKGROUND_COLOUR, mGameMode);
        mGamePane.getChildren().add(mGameArea);
        mGameController = new GameController(mGameArea);
        mUpdateItems.add(mGameArea);

        mTrainer = new Trainer(mGameArea.getmGameBrain(), mGameController, mPopulation);
        mUpdateItems.add(mTrainer);

        AITrainSidebar aiTrainSidebar = new AITrainSidebar(mGameArea, mTrainer, DEFAULT_MARGINS, mSideBarHeight, mSideBarWidth);
        setRight(aiTrainSidebar);
        mUpdateItems.add(aiTrainSidebar);
    }
}
