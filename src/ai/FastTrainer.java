/*
 * Michael Pu
 * TetrisGameAI - FastTrainer
 * ICS3U1 - Mr. Radulovic
 * December 25, 2017
 */

package ai;

import backend.ControllerKeys;
import backend.GameBrain;
import backend.TetrominoBlueprint;
import frontend.common.*;
import javafx.scene.control.TextArea;
import java.util.ArrayList;

public class FastTrainer extends Brain {

    private TextArea mOutputConsole;
    private Population mPopulation;
    private int mCurOrganismIndex;
    private boolean mTraining;
    private long mLastUpdateTime;
    private GameBrain mGameBrain;

    public FastTrainer(TextArea outputConsole, GameArea gameArea, Population population) {
        mOutputConsole = outputConsole;
        mPopulation = population;
        mTraining = true;
        mLastUpdateTime = System.currentTimeMillis();
        goToFirstOrganism();
    }

    private void updateTrainTime() {
        long currentTimeMillis = System.currentTimeMillis();
        mPopulation.addmTrainTime((currentTimeMillis-mLastUpdateTime)/1000.0);
        mLastUpdateTime = currentTimeMillis;
    }

    private void startTrainer() {
        while (mTraining) {
            startTrainGame(mCurOrganism.getmGenome());
            if (mCurOrganismIndex == mPopulation.getNumOrganisms()-1) {
                mPopulation.evolve();
                goToFirstOrganism();
            } else {
                prepareNextOrganism();
            }
        }
    }

    private void startTrainGame(Genome testGenome) {
        //mGameArea.get;
        while (!mGameBrain.getmGrid().checkGameOver()) {
            TetrominoBlueprint newBlueprint = TetrominoBlueprint.getRandomTetrominoBlueprint();
            Tetromino curTetromino = new Tetromino(mGameBrain, newBlueprint, mGameBrain.getNumCols());
            ArrayList<ControllerKeys> bestMoves = getBestMove(mGameBrain.getmGrid(), curTetromino, testGenome);
            for (ControllerKeys move : bestMoves) {
                switch (move) {
                    case LEFT:
                        curTetromino.moveLeft(false);
                        break;
                    case RIGHT:
                        curTetromino.moveRight(false);
                        break;
                    case DROP:
                        curTetromino.moveDown(false);
                }
            }
            mGameBrain.getmGrid().applyTetromino(curTetromino);
            mGameBrain.updateStats();
        }
        mCurOrganism.setmScore(mGameBrain.getmScore());
        mCurOrganism.setmLevel(mGameBrain.getmLevel());
        mCurOrganism.setmLinesCleared(mGameBrain.getmNumLinesCleared());
    }

    private void goToFirstOrganism() {
        mCurOrganismIndex = -1;
        prepareNextOrganism();
    }

    private void prepareNextOrganism() {
        if (mCurOrganismIndex >= 0) {
            mCurOrganism.printFitness();
        }
        mCurOrganismIndex++;
        mCurOrganism = mPopulation.getOrganism(mCurOrganismIndex);
        mCurOrganism.printGenes();
    }

    public Population getmPopulation() {
        return mPopulation;
    }

    public int getmCurOrganismIndex() {
        return mCurOrganismIndex;
    }
}
