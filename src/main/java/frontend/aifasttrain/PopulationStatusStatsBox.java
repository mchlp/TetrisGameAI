/*
 * Michael Pu
 * TetrisGameAI - PopulationStatusStatsBox
 * ICS3U1 - Mr.Radulovic
 * January 20, 2018
 */

package frontend.aifasttrain;

import ai.Brain;
import ai.Population;
import backend.Updatable;
import frontend.common.StatsBar;
import javafx.scene.layout.VBox;

/**
 * Displays the status of the fitness of a population.
 */
public class PopulationStatusStatsBox extends VBox implements Updatable {

	private static final int ELEMENT_SPACING = 5;

	private Population mPopulation;

	private StatsBar mEliteFitnessBar;
	private StatsBar mTopFitnessBar;
	private StatsBar mTotalFitnessBar;
	private StatsBar mMinFitnessBar;
	private StatsBar mAvgFitnessBar;
	private StatsBar mAvgTop25PerFitnessBar;
	private StatsBar mAvgBottom25PerFitnessBar;

	public PopulationStatusStatsBox(Population population, Brain brain) {
		mPopulation = population;

		// set up display properties
		setSpacing(ELEMENT_SPACING);

		// add stats bars
		mEliteFitnessBar = new StatsBar("Elite Fitness", "0");
		getChildren().add(mEliteFitnessBar);

		mTopFitnessBar = new StatsBar("Top Fitness", "0");
		getChildren().add(mTopFitnessBar);

		mAvgTop25PerFitnessBar = new StatsBar("Average Top 25% Fitness", "0");
		getChildren().add(mAvgTop25PerFitnessBar);

		mMinFitnessBar = new StatsBar("Min Fitness", "0");
		getChildren().add(mMinFitnessBar);

		mAvgBottom25PerFitnessBar = new StatsBar("Average Bottom 25% Fitness", "0");
		getChildren().add(mAvgBottom25PerFitnessBar);

		mAvgFitnessBar = new StatsBar("Average Fitness", "0");
		getChildren().add(mAvgFitnessBar);

		mTotalFitnessBar = new StatsBar("Total Fitness", "0");
		getChildren().add(mTotalFitnessBar);

	}

	@Override
	public void update(double deltaTime) {
		mEliteFitnessBar.setValue(Integer.toString(mPopulation.getmEliteFitness()));
		mTopFitnessBar.setValue(Integer.toString(mPopulation.getmTopFitness()));
		mTotalFitnessBar.setValue(Integer.toString(mPopulation.getmTotalFitness()));
		mMinFitnessBar.setValue(Integer.toString(mPopulation.getmMinFitness()));
		mAvgFitnessBar.setValue(Integer.toString(mPopulation.getmAvgFitness()));
		mAvgTop25PerFitnessBar.setValue(Integer.toString(mPopulation.getmTop25PerFitness()));
		mAvgBottom25PerFitnessBar.setValue(Integer.toString(mPopulation.getmBottom25PerFitness()));
	}
}
