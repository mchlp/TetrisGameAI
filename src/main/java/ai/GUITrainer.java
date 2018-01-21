/*
 * Michael Pu
 * TetrisGameAI - GUITrainer
 * ICS3U1 - Mr.Radulovic
 * January 18, 2018
 */

package ai;

import java.io.IOException;

import backend.ControllerKeys;
import backend.GameProcessor;
import frontend.common.GameController;
import javafx.scene.control.Alert;
import javafx.scene.layout.Region;

/**
 * Trains an AI {@link Population} and displays the gameplay graphically.
 * Extends the {@link GUIBrain} class, which is used to determines the best move
 * and send it to the {@link GameController}. Each organism in the population
 * plays one game during each generation before the population is bred.
 */
public class GUITrainer extends GUIBrain {

	/**
	 * The population to train
	 */
	private Population mPopulation;

	/**
	 * The index of the organism currently being tested.
	 */
	private int mCurOrganismIndex;

	/**
	 * Initializes a training session using the GUITrainer.
	 *
	 * @param gameProcessor
	 *            The {@link GameProcessor} of the game that being played.
	 * @param gameController
	 *            The {@link GameController} linked to the game.
	 * @param population
	 *            The {@link Population} to be trained.
	 */
	public GUITrainer(GameProcessor gameProcessor, GameController gameController, Population population) {
		super(gameProcessor, gameController, true);
		mPopulation = population;
		goToFirstOrganism();
	}

	/**
	 * If an organism is training, updates the training time of the population.
	 * Otherwise, updates the statistics of the organism and advances to the next
	 * organism. If all organisms have been tested, {@link #mPopulation} is evolved
	 * and testing restarts from the first organism.
	 *
	 * @param deltaTime
	 *            The amount of time that has passed since the last time this
	 *            function was called in seconds.
	 */
	public void update(double deltaTime) {
		super.update(deltaTime);
		switch (mGameProcessor.getmGameState()) {
		case PLAYING:
			// if the current organism is still playing a game, add the time passed to the
			// total training time
			mPopulation.addmTrainTime(deltaTime);
			break;
		case OVER:
			// update the statistics of the organism
			if (mGameProcessor.getmScore() > mCurOrganism.getmMaxScore()) {
				mCurOrganism.setmMaxScore(mGameProcessor.getmScore());
			}
			if (mGameProcessor.getmLevel() > mCurOrganism.getmMaxLevel()) {
				mCurOrganism.setmMaxLevel(mGameProcessor.getmLevel());
			}
			if (mGameProcessor.getmNumLinesCleared() > mCurOrganism.getmMaxLinesCleared()) {
				mCurOrganism.setmMaxLinesCleared(mGameProcessor.getmNumLinesCleared());
			}

			// add the score to the list of scores for the organism
			mCurOrganism.addScore(mGameProcessor.getmScore());

			if (mCurOrganismIndex == mPopulation.getNumOrganisms() - 1) {
				// if the organism is the last organism in the population
				try {
					// evolve the population
					mPopulation.evolve();
				} catch (IOException e) {
					// if there is an error writing to the file, show error window
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Error writing population to file.");
					alert.setHeaderText("Error writing population to file.");
					alert.setContentText("The file specified may be read-only or no longer available. "
							+ "Try returning to the main main.java.frontend.menu and selecting another file to save the population.");
					alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
					alert.showAndWait();
				}
				// restart the training from the first organism
				goToFirstOrganism();
			} else {
				// if the organism is not the last organism, advance to the next organism
				prepareNextOrganism();
			}
			// restart the game for the next organism
			mGameController.keyPressed(ControllerKeys.RESTART);
			break;
		default:
			break;
		}
	}

	/**
	 * Restarts the training of the population. Sets the {@link #mCurOrganism} to
	 * the first organism in the population and updates the
	 * {@link #mCurOrganismIndex}.
	 */
	private void goToFirstOrganism() {
		mCurOrganismIndex = -1;
		prepareNextOrganism();
	}

	/**
	 * Sets the {@link #mCurOrganism} to the next organism in the population and
	 * updates the {@link #mCurOrganismIndex}.
	 */
	private void prepareNextOrganism() {
		mCurOrganismIndex++;
		mCurOrganism = mPopulation.getOrganism(mCurOrganismIndex);
	}

	public Population getmPopulation() {
		return mPopulation;
	}

	public int getmCurOrganismIndex() {
		return mCurOrganismIndex;
	}
}
