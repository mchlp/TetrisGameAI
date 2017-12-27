/*
 * Michael Pu
 * TetrisGameAI - FastTrainer
 * ICS3U1 - Mr. Radulovic
 * December 25, 2017
 */

package ai;

import backend.ControllerKeys;
import backend.GameBrain;
import frontend.common.GameState;
import java.util.ArrayList;

public class FastTrainer extends Brain {

    private static final int NUM_GAMES_PER_SESSION = 5;

    private Population mPopulation;
    private int mCurOrganismIndex;
    private boolean mTraining;
    private long mLastUpdateTime;
    private int mTopScore;
    private boolean mCurrentlyTraining;
    private int mMaxNumMoves;

    public FastTrainer(GameBrain gameBrain, Population population) {
        super(gameBrain);
        mPopulation = population;
        mTraining = true;
        mLastUpdateTime = System.currentTimeMillis();
        mTopScore = 0;
        goToFirstOrganism();
    }

    private void updateTrainTime() {
        long currentTimeMillis = System.currentTimeMillis();
        mPopulation.addmTrainTime((currentTimeMillis-mLastUpdateTime)/1000.0);
        mLastUpdateTime = currentTimeMillis;
    }

    @Override
    public void update(double deltaTime) {
        if (mTraining) {
            if (!mCurrentlyTraining) {
                mCurrentlyTraining = true;
                for (int i=0; i<NUM_GAMES_PER_SESSION; i++) {
                    startGame(mCurOrganism.getmGenome());
                }
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

    private void startGame(Genome testGenome) {
        updateTrainTime();
        mGameBrain.newGame();
        mGameBrain.createTetromino();
        int moves=0;
        while (mGameBrain.getmGameState() == GameState.PLAYING) {
            ArrayList<ControllerKeys> bestMoves = getBestMove(mGameBrain.getmGrid(), mGameBrain.getmCurTetromino(), testGenome);
            for (ControllerKeys move : bestMoves) {
                switch (move) {
                    case LEFT:
                        mGameBrain.moveLeft();
                        break;
                    case RIGHT:
                        mGameBrain.moveRight();
                        break;
                    case ROTATE:
                        mGameBrain.rotate();
                        break;
                    case DROP:
                        mGameBrain.drop();
                        break;
                }
            }
            mGameBrain.moveDown();
            mGameBrain.update();
            moves++;
        }

        if (moves > mMaxNumMoves) {
            mMaxNumMoves = moves;
            System.out.println(moves);
        }
        if (mGameBrain.getmScore() > mCurOrganism.getmMaxScore()) {
            mCurOrganism.setmMaxScore(mGameBrain.getmScore());
        }
        if (mGameBrain.getmLevel() > mCurOrganism.getmMaxLevel()) {
            mCurOrganism.setmMaxLevel(mGameBrain.getmLevel());
        }
        if (mGameBrain.getmNumLinesCleared() > mCurOrganism.getmMaxLinesCleared()) {
            mCurOrganism.setmMaxLinesCleared(mGameBrain.getmNumLinesCleared());
        }
        if (mCurOrganism.getmMaxScore() > mTopScore) {
            mTopScore = mCurOrganism.getmMaxScore();
        }
        mCurOrganism.addTotalScore(mGameBrain.getmScore());
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

    public void toggleTraining() {
        mTraining = !mTraining;
        if (mTraining) {
            mLastUpdateTime = System.currentTimeMillis();
        }
    }
}
