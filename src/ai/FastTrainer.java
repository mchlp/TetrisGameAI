/*
 * Michael Pu
 * TetrisGameAI - FastTrainer
 * ICS3U1 - Mr.Radulovic
 * December 28, 2017
 */

package ai;

import backend.ControllerKeys;
import backend.GameProcessor;
import backend.GameState;

import java.util.ArrayList;

/**
 * Trains an AI {@link Population} without display the gameplay graphically, which allows the training to take place at
 * a much faster rate. Extends the {@link Brain} class, which is used to determines the best move. Each organism in the
 * population plays multiple games ({@link #NUM_GAMES_PER_SESSION} during each generation before the population is
 * bred.
 */
public class FastTrainer extends Brain {

    /**
     * The number of games each organism plays in each generation
     */
    private static final int NUM_GAMES_PER_SESSION = 5;

    private Population mPopulation;
    private int mCurOrganismIndex;
    private boolean mTraining;
    private long mLastUpdateTime;
    private int mTopScore;
    private boolean mCurrentlyTraining;
    private int mMaxNumMoves;
    private int mGameNum;

    public FastTrainer(GameProcessor gameProcessor, Population population) {
        super(gameProcessor, true);
        mPopulation = population;
        mTraining = true;
        mLastUpdateTime = System.currentTimeMillis();
        mTopScore = 0;
        goToFirstOrganism();
    }

    private void updateTrainTime() {
        long currentTimeMillis = System.currentTimeMillis();
        mPopulation.addmTrainTime((currentTimeMillis - mLastUpdateTime) / 1000.0);
        mLastUpdateTime = currentTimeMillis;
    }

    @Override
    public void update(double deltaTime) {
        if (mTraining) {
            if (!mCurrentlyTraining) {
                mCurrentlyTraining = true;
                mGameNum = 0;
            }
            if (mGameNum < NUM_GAMES_PER_SESSION) {
                startGame();
                mGameNum++;
            } else {
                mCurOrganism.setmGeneration(mPopulation.getmGeneration());
                if (mCurOrganismIndex == mPopulation.getNumOrganisms() - 1) {
                    mPopulation.evolve();
                    goToFirstOrganism();
                } else {
                    prepareNextOrganism();
                }
                mCurrentlyTraining = false;
            }
        }
    }

    private void startGame() {
        mGameProcessor.newGame();
        mGameProcessor.createTetromino();
        int moves = 0;
        while (mGameProcessor.getmGameState() == GameState.PLAYING) {
            updateTrainTime();
            ArrayList<ControllerKeys> bestMoves = getBestMove(mGameProcessor.getmGrid(), mGameProcessor.getmCurTetromino());
            for (ControllerKeys move : bestMoves) {
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
            mGameProcessor.moveDown();
            mGameProcessor.update();
            moves++;
        }

        if (moves > mMaxNumMoves) {
            mMaxNumMoves = moves;
            System.out.println(moves);
        }
        if (mGameProcessor.getmScore() > mCurOrganism.getmMaxScore()) {
            mCurOrganism.setmMaxScore(mGameProcessor.getmScore());
        }
        if (mGameProcessor.getmLevel() > mCurOrganism.getmMaxLevel()) {
            mCurOrganism.setmMaxLevel(mGameProcessor.getmLevel());
        }
        if (mGameProcessor.getmNumLinesCleared() > mCurOrganism.getmMaxLinesCleared()) {
            mCurOrganism.setmMaxLinesCleared(mGameProcessor.getmNumLinesCleared());
        }
        if (mCurOrganism.getmMaxScore() > mTopScore) {
            mTopScore = mCurOrganism.getmMaxScore();
        }
        mCurOrganism.addScore(mGameProcessor.getmScore());
    }

    private void goToFirstOrganism() {
        mCurOrganismIndex = -1;
        prepareNextOrganism();
    }

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
