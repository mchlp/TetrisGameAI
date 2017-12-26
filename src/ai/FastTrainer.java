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
import javafx.scene.control.TextArea;
import java.util.ArrayList;

public class FastTrainer extends Brain {

    private TextArea mOutputConsole;
    private Population mPopulation;
    private int mCurOrganismIndex;
    private boolean mTraining;
    private long mLastUpdateTime;
    private int mTopScore;

    public FastTrainer(TextArea outputConsole, GameBrain gameBrain, Population population) {
        super(gameBrain);
        mOutputConsole = outputConsole;
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
            startGame(mCurOrganism.getmGenome());
            if (mCurOrganismIndex == mPopulation.getNumOrganisms()-1) {
                mPopulation.evolve();
                goToFirstOrganism();
            } else {
                prepareNextOrganism();
            }
        }
    }

    private void startGame(Genome testGenome) {
        updateTrainTime();
        mGameBrain.newGame();
        mGameBrain.createTetromino();
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
        }
        mCurOrganism.setmScore(mGameBrain.getmScore());
        mCurOrganism.setmLevel(mGameBrain.getmLevel());
        mCurOrganism.setmLinesCleared(mGameBrain.getmNumLinesCleared());
        if (mCurOrganism.calculateFitness() > mTopScore) {
            mTopScore = mCurOrganism.calculateFitness();
        }
    }

    private void goToFirstOrganism() {
        mCurOrganismIndex = -1;
        prepareNextOrganism();
    }

    private void prepareNextOrganism() {
        if (mCurOrganismIndex >= 0) {
            mOutputConsole.appendText(mCurOrganism.printFitness());
        }
        mCurOrganismIndex++;
        mCurOrganism = mPopulation.getOrganism(mCurOrganismIndex);
        mOutputConsole.appendText(mCurOrganism.printGenes());
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
