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
import frontend.common.GameMode;

import java.io.File;

public class AITrainGameWindow extends GameWindow {

    private Population mPopulation;
    private Trainer mTrainer;

    public AITrainGameWindow(double height, double width) {
        super(height, width, GameMode.AI_TRAINER);

        File saveFile = new File("~/Desktop/testAI.ser");
        mPopulation = new Population(saveFile);

        mTrainer = new Trainer(mGameArea, mGameController, mPopulation);
        mUpdateItems.add(mTrainer);

        AITrainSidebar aiTrainSidebar = new AITrainSidebar(mGameArea, mTrainer, DEFAULT_MARGINS, mSideBarHeight, mSideBarWidth);
        setRight(aiTrainSidebar);
        mUpdateItems.add(aiTrainSidebar);
    }
}
