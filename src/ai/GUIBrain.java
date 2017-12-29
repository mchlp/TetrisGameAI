/*
 * Michael Pu
 * TetrisGameAI - GUIBrain
 * ICS3U1 - Mr.Radulovic
 * December 28, 2017
 */

package ai;

import backend.ControllerKeys;
import backend.GameBrain;
import frontend.common.GameController;

import java.util.ArrayList;

public abstract class GUIBrain extends Brain {

    protected int mPrevScore;
    protected GameController mGameController;

    public GUIBrain(GameBrain gameBrain, GameController gameController, boolean fastMode) {
        super(gameBrain, fastMode);
        mGameController = gameController;
        mPrevScore = -1;
    }

    @Override
    public void update(double deltaTime) {
        switch (mGameBrain.getmGameState()) {
            case PLAYING:
                if (mPrevScore != mGameBrain.getmScore()) {
                    ArrayList<ControllerKeys> moves = getBestMove(mGameBrain.getmGrid(), mGameBrain.getmCurTetromino(), mCurOrganism.getmGenome());
                    for (ControllerKeys move : moves) {
                        mGameController.keyPressed(move);
                    }
                    mPrevScore = mGameBrain.getmScore();
                }
                break;
            case OVER:
                mCurOrganism.setmMaxScore(mGameBrain.getmScore());
                mCurOrganism.setmMaxLevel(mGameBrain.getmLevel());
                mCurOrganism.setmMaxLinesCleared(mGameBrain.getmNumLinesCleared());
        }
    }

    public GameController getmGameController() {
        return mGameController;
    }
}
