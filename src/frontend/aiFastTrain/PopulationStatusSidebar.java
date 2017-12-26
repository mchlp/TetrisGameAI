/*
 * Michael Pu
 * TetrisGameAI - PopulationStatusSidebar
 * ICS3U1 - Mr. Radulovic
 * December 26, 2017
 */

package frontend.aiFastTrain;

import ai.FastTrainer;
import ai.Population;
import frontend.base.Sidebar;
import javafx.scene.control.TextArea;

public class PopulationStatusSidebar extends Sidebar {

    private FastTrainer mFastTrainer;
    private Population mPopulation;
    private TextArea mOutputConsole;


    public PopulationStatusSidebar(double margins, double sideBarHeight, double sideBarWidth, Population population, FastTrainer fastTrainer, TextArea outputConsole) {
        super(margins, sideBarHeight, sideBarWidth);

        mPopulation = population;
        mFastTrainer = fastTrainer;
        mOutputConsole = outputConsole;

        PopulationStatusStatsBox populationStatusStatsBox = new PopulationStatusStatsBox(mPopulation, mFastTrainer);
        getChildren().add(populationStatusStatsBox);
        mUpdateItems.add(populationStatusStatsBox);

        //getChildren().add(mOutputConsole);
    }
}
