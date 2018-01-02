/*
 * Michael Pu
 * TetrisGameAI - FastTrainer
 * ICS3U1 - Mr. Radulovic
 * January 01, 2018
 */

package ai;

import backend.ControllerKeys;
import backend.GameProcessor;
import backend.GameState;
import javafx.scene.control.Alert;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Trains an AI {@link Population} without display the gameplay graphically, which allows the training to take place at
 * a much faster rate. Extends the {@link Brain} class, which is used to determines the best move. Each organism in the
 * population plays {@value #NUM_GAMES_PER_ORGANISM} games during each generation before the population is bred.
 */
public class FastTrainer extends Brain {

    /**
     * The number of games each organism plays in each generation.
     */
    private static final int NUM_GAMES_PER_ORGANISM = 5;

    /**
     * The population to train.
     */
    private Population mPopulation;

    /**
     * The index of the organism currently being tested.
     */
    private int mCurOrganismIndex;

    /**
     * <code>true</code> if training is currently taking place, <code>false</code> if paused.
     */
    private boolean mTraining;

    /**
     * The time when the last move in the game was made in milliseconds. Used to keep track of the amount of time the
     * population has been training for.
     */
    private long mLastUpdateTime;

    /**
     * The highest score obtained by an organism during this training session.
     */
    private int mTopScore;

    /**
     * The highest number of moves made by an organism during one game during this training session.
     */
    private int mMaxNumMoves;

    /**
     * The game number that the organism currently being trained is on. When it reaches {@value
     * #NUM_GAMES_PER_ORGANISM}, it is reset back to zero for the next organism.
     */
    private int mGameNum;

    /**
     * Initializes a training session using the FastTrainer.
     *
     * @param gameProcessor The {@link GameProcessor} of the game that being played.
     * @param population    The {@link Population} to be trained.
     */
    public FastTrainer(GameProcessor gameProcessor, Population population) {
        super(gameProcessor, true);
        mPopulation = population;
        mTraining = true;
        mLastUpdateTime = System.currentTimeMillis();
        mTopScore = 0;
        goToFirstOrganism();
    }

    /**
     * Updates the time when the last move was made in the game, which is used to update the amount of time that
     * population has been training for.
     */
    private void updateTrainTime() {
        long currentTimeMillis = System.currentTimeMillis();
        // add the time since the last move was made to the total time the population has been training for
        mPopulation.addmTrainTime((currentTimeMillis - mLastUpdateTime) / 1000.0);
        mLastUpdateTime = currentTimeMillis;
    }

    /**
     * Starts a game to test the {@link #mCurOrganism}. If the {@link #mCurOrganism} has played {@value
     * NUM_GAMES_PER_ORGANISM} games, the next organism in {@link #mPopulation} is assigned to {@link #mCurOrganism}. If
     * all organisms in {@link #mPopulation} have been tested, {@link #mPopulation} is evolved and testing restarts from
     * the first organism.
     *
     * @param deltaTime The amount of time that has passed since the last time this function was called in seconds.
     */
    @Override
    public void update(double deltaTime) {
        if (mTraining) {
            // if currently training (not paused)
            if (mGameNum < NUM_GAMES_PER_ORGANISM) {
                // if the current organism has not finished all of its games, start a game for that organism
                startGame();
                mGameNum++;
            } else {
                // if the organism has finished all of its games
                if (mCurOrganismIndex == mPopulation.getNumOrganisms() - 1) {
                    // if all the organisms in the population have been tested, evolve the population and restart
                    try {
                        mPopulation.evolve();
                    } catch (IOException e) {
                        // if there is an error writing to the file, show an error window
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error writing population to file.");
                        alert.setHeaderText("Error writing population to file.");
                        alert.setContentText("The file specified may be read-only or no longer available. " +
                                "Try returning to the main frontend.menu and selecting another file to save the population.");
                        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                        alert.show();
                    }
                    goToFirstOrganism();
                } else {
                    // advance to the next organism in the population
                    prepareNextOrganism();
                }
                // reset the number of games played by the current organism
                mGameNum = 0;
            }
        }
    }

    /**
     * Starts a game to test the {@link #mCurOrganism}.
     */
    private void startGame() {
        // restart a game in the GameProcessor
        mGameProcessor.newGame();
        mGameProcessor.createTetromino();
        // reset the number of moves made
        int moves = 0;
        while (mGameProcessor.getmGameState() == GameState.PLAYING) {
            // if the game is not over
            updateTrainTime();
            // get a list of the best moves to make
            ArrayList<ControllerKeys> bestMoves = getBestMove(mGameProcessor.getmGrid(), mGameProcessor.getmCurTetromino());
            for (ControllerKeys move : bestMoves) {
                // for each move in the optimal set of moves, call its corresponding function in the GameProcessor
                switch (move) {
                    case LEFT:
                        mGameProcessor.moveLeft();
                        break;
                    case RIGHT:
                        mGameProcessor.moveRight();
                        break;
                    case ROTATE:
                        mGameProcessor.rotate();
                        break;
                    case DROP:
                        mGameProcessor.drop();
                        break;
                }
            }
            // update the GameProcessor after the moves are made
            mGameProcessor.update();
            moves++;
        }

        if (moves > mMaxNumMoves) {
            // if the number of moves made this game is higher than the maximum number of moves
            mMaxNumMoves = moves;
            System.out.println(moves);
        }

        // update the max score of the training session
        if (mGameProcessor.getmScore() > mCurOrganism.getmMaxScore()) {
            mCurOrganism.setmMaxScore(mGameProcessor.getmScore());
        }

        // update the max level of the organism
        if (mGameProcessor.getmLevel() > mCurOrganism.getmMaxLevel()) {
            mCurOrganism.setmMaxLevel(mGameProcessor.getmLevel());
        }

        // update the max lines cleared of the organism
        if (mGameProcessor.getmNumLinesCleared() > mCurOrganism.getmMaxLinesCleared()) {
            mCurOrganism.setmMaxLinesCleared(mGameProcessor.getmNumLinesCleared());
        }

        // update the max score of the organism
        if (mCurOrganism.getmMaxScore() > mTopScore) {
            mTopScore = mCurOrganism.getmMaxScore();
        }

        // add the score of this game to the organism's total score
        mCurOrganism.addScore(mGameProcessor.getmScore());
    }

    /**
     * Restarts the training of the population. Sets the {@link #mCurOrganism} to the first organism in the population
     * and updates the {@link #mCurOrganismIndex}.
     */
    private void goToFirstOrganism() {
        mCurOrganismIndex = -1;
        prepareNextOrganism();
    }

    /**
     * Sets the {@link #mCurOrganism} to the next organism in the population and updates the {@link
     * #mCurOrganismIndex}.
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

    public int getmTopScore() {
        return mTopScore;
    }

    public boolean ismTraining() {
        return mTraining;
    }

    public void setmTraining(boolean training) {
        if (training) {
            mTraining = true;
            mLastUpdateTime = System.currentTimeMillis();
        } else {
            mTraining = false;
        }
    }
}
