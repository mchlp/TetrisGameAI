/*
 * Michael Pu
 * TetrisGameAI - AIFastTrainGameWindow
 * ICS3U1 - Mr. Radulovic
 * December 25, 2017
 */

package frontend.aiFastTrain;

import ai.FastTrainer;
import ai.Population;
import backend.GameBrain;
import backend.Updatable;
import frontend.base.GameWindow;
import frontend.common.GameMode;
import javafx.scene.control.TextArea;

import java.io.File;

public class AIFastTrainGameWindow extends GameWindow implements Updatable {

    private Population mPopulation;
    private FastTrainer mFastTrainer;
    private GameBrain mGameBrain;

    public AIFastTrainGameWindow(double height, double width) {
        super(height, width, GameMode.AI_FAST_TRAINER);

        File saveFile = new File("/home/mpu/Desktop/fastTestAI.ser");
        mPopulation = new Population(saveFile);

        mGameBrain = new GameBrain(GameMode.AI_FAST_TRAINER);

        TextArea outputConsole = new TextArea();
        outputConsole.setEditable(false);
        mGamePane.getChildren().add(outputConsole);

        mFastTrainer = new FastTrainer(outputConsole, mGameBrain, mPopulation);
        mUpdateItems.add(mFastTrainer);

        AIFastTrainSidebar aiFastTrainSidebar = new AIFastTrainSidebar(mGameBrain, mFastTrainer, DEFAULT_MARGINS, mSideBarHeight, mSideBarWidth);
        setRight(aiFastTrainSidebar);
        mUpdateItems.add(aiFastTrainSidebar);
    }
}
