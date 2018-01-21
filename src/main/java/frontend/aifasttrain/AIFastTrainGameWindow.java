/*
 * Michael Pu
 * TetrisGameAI - AIFastTrainGameWindow
 * ICS3U1 - Mr.Radulovic
 * January 20, 2018
 */

package frontend.aifasttrain;

import ai.FastTrainer;
import ai.Population;
import backend.GameProcessor;
import backend.GameMode;
import frontend.base.TwoPanelGameWindow;
import javafx.stage.Stage;

/**
 * Game window for AI fast train mode.
 */
public class AIFastTrainGameWindow extends TwoPanelGameWindow {

    private Population mPopulation;
    private FastTrainer mFastTrainer;

    public AIFastTrainGameWindow(Population population, Stage stage, double height, double width) {
        super(stage, height, width, GameMode.AI_FAST_TRAINER);

        mWindowTitle = "Tetris Game - AI Fast Train Version";

        mPopulation = population;

        mGameProcessor = new GameProcessor(GameMode.AI_FAST_TRAINER);

        mFastTrainer = new FastTrainer(mGameProcessor, mPopulation);
        mUpdateItems.add(mFastTrainer);

        // add population status sidebar (left) instead of game area display (does not display game play)
        PopulationStatusSidebar populationStatusSidebar = new PopulationStatusSidebar(DEFAULT_MARGINS, height*0.5, width*0.5, mPopulation, mFastTrainer);
        setLeft(populationStatusSidebar);
        mUpdateItems.add(populationStatusSidebar);

        // add game sidebar (right)
        AIFastTrainSidebar aiFastTrainSidebar = new AIFastTrainSidebar(this, mGameProcessor, mFastTrainer, DEFAULT_MARGINS, mSideBarHeight, mSideBarWidth);
        setRight(aiFastTrainSidebar);
        mUpdateItems.add(aiFastTrainSidebar);
    }
}
