/*
 * Michael Pu
 * TetrisGameAI - GUIBrain
 * ICS3U1 - Mr. Radulovic
 * December 26, 2017
 */

package ai;

import backend.ControllerKeys;
import backend.Updatable;
import frontend.common.GameArea;
import frontend.common.GameController;

import java.util.ArrayList;

public abstract class GUIBrain extends Brain implements Updatable {

    protected int mPrevScore;
    protected GameController mGameController;
    protected GameArea mGameArea;

    public GUIBrain(GameArea gameArea, GameController gameController) {
        mGameArea = gameArea;
        mGameController = gameController;
        mPrevScore = -1;
    }

    @Override
    public void update(double deltaTime) {
        switch (mGameArea.getmGameState()) {
            case PLAYING:
                if (mPrevScore != mGameArea.getmScore()) {
                    ArrayList<ControllerKeys> moves = getBestMove(mGameArea.getmGrid(), mGameArea.getmCurTetromino(), mCurOrganism.getmGenome());
                    for (ControllerKeys move : moves) {
                        mGameController.keyPressed(move);
                    }
                    mPrevScore = mGameArea.getmScore();
                }
                break;

            case OVER:
                mCurOrganism.setmScore(mGameArea.getmScore());
                mCurOrganism.setmLevel(mGameArea.getmLevel());
                mCurOrganism.setmLinesCleared(mGameArea.getmNumLinesCleared());
        }
    }

    public GameController getmGameController() {
        return mGameController;
    }
}
