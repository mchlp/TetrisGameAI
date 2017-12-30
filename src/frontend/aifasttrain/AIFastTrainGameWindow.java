/*
 * Michael Pu
 * TetrisGameAI - AIFastTrainGameWindow
 * ICS3U1 - Mr.Radulovic
 * December 28, 2017
 */

package frontend.aifasttrain;

import ai.FastTrainer;
import ai.Population;
import backend.GameBrain;
import backend.GameMode;
import frontend.base.TwoPanelGameWindow;
import javafx.stage.Stage;

public class AIFastTrainGameWindow extends TwoPanelGameWindow {

    private Population mPopulation;
    private FastTrainer mFastTrainer;
    private GameBrain mGameBrain;

    public AIFastTrainGameWindow(Population population, Stage stage, double height, double width) {
        super(stage, height, width, GameMode.AI_FAST_TRAINER);

        mWindowTitle = "Tetris Game - AI Fast Train Version";

        mPopulation = population;

        mGameBrain = new GameBrain(GameMode.AI_FAST_TRAINER);

        mFastTrainer = new FastTrainer(mGameBrain, mPopulation);
        mUpdateItems.add(mFastTrainer);

        PopulationStatusSidebar populationStatusSidebar = new PopulationStatusSidebar(DEFAULT_MARGINS, height*0.5, width*0.5, mPopulation, mFastTrainer);
        setLeft(populationStatusSidebar);
        mUpdateItems.add(populationStatusSidebar);

        AIFastTrainSidebar aiFastTrainSidebar = new AIFastTrainSidebar(this, mGameBrain, mFastTrainer, DEFAULT_MARGINS, mSideBarHeight, mSideBarWidth);
        setRight(aiFastTrainSidebar);
        mUpdateItems.add(aiFastTrainSidebar);
    }
}
