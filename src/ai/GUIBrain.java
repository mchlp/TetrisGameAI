/*
 * Michael Pu
 * TetrisGameAI - GUIBrain
 * ICS3U1 - Mr.Radulovic
 * December 28, 2017
 */

package ai;

import backend.ControllerKeys;
import backend.GameProcessor;
import frontend.common.GameController;

import java.util.ArrayList;

public abstract class GUIBrain extends Brain {

    protected int mPrevScore;
    protected GameController mGameController;

    public GUIBrain(GameProcessor gameProcessor, GameController gameController, boolean fastMode) {
        super(gameProcessor, fastMode);
        mGameController = gameController;
        mPrevScore = -1;
    }

    @Override
    public void update(double deltaTime) {
        switch (mGameProcessor.getmGameState()) {
            case PLAYING:
                if (mPrevScore != mGameProcessor.getmScore()) {
                    ArrayList<ControllerKeys> moves = getBestMove(mGameProcessor.getmGrid(), mGameProcessor.getmCurTetromino());
                    for (ControllerKeys move : moves) {
                        mGameController.keyPressed(move);
                    }
                    mPrevScore = mGameProcessor.getmScore();
                }
                break;
            case OVER:
                mCurOrganism.setmMaxScore(mGameProcessor.getmScore());
                mCurOrganism.setmMaxLevel(mGameProcessor.getmLevel());
                mCurOrganism.setmMaxLinesCleared(mGameProcessor.getmNumLinesCleared());
        }
    }

    public GameController getmGameController() {
        return mGameController;
    }
}
