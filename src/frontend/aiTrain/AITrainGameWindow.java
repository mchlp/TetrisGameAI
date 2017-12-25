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

import java.io.File;

public class AITrainGameWindow extends GameWindow {

    private Population mPopulation;

    public AITrainGameWindow(double height, double width) {
        super(height, width);

        File saveFile = new File("test.ser");
        mPopulation = new Population(saveFile);

        Trainer aiTrainer = new Trainer(mGameArea, mGameController, mPopulation);
        mUpdateItems.add(aiTrainer);

        AITrainSidebar aiTrainSidebar = new AITrainSidebar(mGameArea, DEFAULT_MARGINS, mSideBarHeight, mSideBarWidth);
        setRight(aiTrainSidebar);
        mUpdateItems.add(aiTrainSidebar);
    }
}
