/*
 * Michael Pu
 * TetrisGameAI - PopulationStatusSidebar
 * ICS3U1 - Mr.Radulovic
 * December 28, 2017
 */

package frontend.aiFastTrain;

import ai.FastTrainer;
import ai.Population;
import backend.Updatable;
import frontend.base.Sidebar;
import frontend.common.OrganismStatusBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class PopulationStatusSidebar extends VBox implements Updatable {

    private ArrayList<Updatable> mUpdateItems;
    private FastTrainer mFastTrainer;
    private Population mPopulation;
    private OrganismStatusBox mOrganismStatusBox;


    public PopulationStatusSidebar(double margins, double sideBarHeight, double sideBarWidth, Population population, FastTrainer fastTrainer) {
        super(margins);

        setPadding(new Insets(margins));
        setAlignment(Pos.TOP_CENTER);
        setPrefHeight(sideBarHeight);
        setPrefWidth(sideBarWidth);
        mUpdateItems = new ArrayList<>();

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
        for (Updatable updatable : mUpdateItems) {
            updatable.update(deltaTime);
        }
        if (mPopulation.getElite() != null) {
            mOrganismStatusBox.setmOrganism(mPopulation.getElite());
        }
    }
}
