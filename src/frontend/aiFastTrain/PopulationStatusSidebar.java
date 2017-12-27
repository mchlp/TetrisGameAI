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
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class PopulationStatusSidebar extends Sidebar {

    private FastTrainer mFastTrainer;
    private Population mPopulation;
    private OrganismStatusBox mOrganismStatusBox;


    public PopulationStatusSidebar(double margins, double sideBarHeight, double sideBarWidth, Population population, FastTrainer fastTrainer) {
        super(margins, sideBarHeight, sideBarWidth);

        mPopulation = population;
        mFastTrainer = fastTrainer;

        PopulationStatusStatsBox populationStatusStatsBox = new PopulationStatusStatsBox(mPopulation, mFastTrainer);
        getChildren().add(populationStatusStatsBox);
        mUpdateItems.add(populationStatusStatsBox);

        mOrganismStatusBox = new OrganismStatusBox("Elite Organism Data", mPopulation.getOrganism(0));
        getChildren().add(mOrganismStatusBox);
        mUpdateItems.add(mOrganismStatusBox);
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        if (mPopulation.getElite() != null) {
            mOrganismStatusBox.setmOrganism(mPopulation.getElite());
        }
    }
}
