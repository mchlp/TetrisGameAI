/*
 * Michael Pu
 * TetrisGameAI - AIFastTrainGameWindow
 * ICS3U1 - Mr. Radulovic
 * December 26, 2017
 */

package frontend.aiFastTrain;

import ai.FastTrainer;
import ai.Population;
import backend.GameBrain;
import backend.Updatable;
import frontend.base.GameWindow;
import frontend.common.GameMode;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;

import java.io.File;

public class AIFastTrainGameWindow extends GameWindow implements Updatable {

    private static final int OUTPUT_CONSOLE_FONT_SIZE = 10;

    private Population mPopulation;
    private FastTrainer mFastTrainer;
    private GameBrain mGameBrain;

    public AIFastTrainGameWindow(double height, double width) {
        super(height, width, GameMode.AI_FAST_TRAINER);

        File saveFile = new File("/home/mpu/Desktop/fastTestAI.ser");

        //mPopulation = new Population(saveFile);
        mPopulation = Population.loadPopulationFromFile(new File("/home/mpu/Desktop/fastTestAI.ser"));

        mGameBrain = new GameBrain(GameMode.AI_FAST_TRAINER);

        mFastTrainer = new FastTrainer(mGameBrain, mPopulation);
        mUpdateItems.add(mFastTrainer);

        PopulationStatusSidebar populationStatusSidebar = new PopulationStatusSidebar(DEFAULT_MARGINS, mCanvasHeight, mCanvasWidth, mPopulation, mFastTrainer);
        setLeft(populationStatusSidebar);
        mUpdateItems.add(populationStatusSidebar);

        AIFastTrainSidebar aiFastTrainSidebar = new AIFastTrainSidebar(mGameBrain, mFastTrainer, DEFAULT_MARGINS, mSideBarHeight, mSideBarWidth);
        setRight(aiFastTrainSidebar);
        mUpdateItems.add(aiFastTrainSidebar);
    }
}
