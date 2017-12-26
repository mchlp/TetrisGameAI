/*
 * Michael Pu
 * TetrisGameAI - AIFastTrainGameWindow
 * ICS3U1 - Mr. Radulovic
 * December 25, 2017
 */

package frontend.aiFastTrain;

import ai.FastTrainer;
import ai.Population;
import ai.Trainer;
import backend.Updatable;
import com.sun.org.apache.bcel.internal.generic.POP;
import frontend.aiTrain.AITrainSidebar;
import frontend.base.GameWindow;
import frontend.common.GameMode;
import javafx.scene.layout.Pane;

import java.io.File;

public class AIFastTrainGameWindow extends GameWindow implements Updatable {

    private Population mPopulation;
    private FastTrainer mFastTrainer;

    public AIFastTrainGameWindow(double height, double width) {
        super(height, width, GameMode.AI_FAST_TRAINER);

        File saveFile = new File("/home/mpu/Desktop/fastTestAI.ser");
        mPopulation = new Population(saveFile);

        //mFastTrainer = new FastTrainer(mGameArea, mGameController, mPopulation);
        //mUpdateItems.add(mFastTrainer);

        //AIF aiTrainSidebar = new AITrainSidebar(mGameArea, mTrainer, DEFAULT_MARGINS, mSideBarHeight, mSideBarWidth);
        //setRight(aiTrainSidebar);
        //mUpdateItems.add(aiTrainSidebar);
    }

    @Override
    public void update(double deltaTime) {

    }
}
