/*
 * Michael Pu
 * TetrisGameAI - GUIBrain
 * ICS3U1 - Mr.Radulovic
 * January 13, 2018
 */

package ai;

import backend.ControllerKeys;
import backend.GameProcessor;
import frontend.common.GameController;

import java.util.ArrayList;

/**
 * An abstract class that acts wrapper around the {@link Brain} which sends moves calculated by the {@link Brain} to the
 * {@link GameController}.
 */
public abstract class GUIBrain extends Brain {

    /**
     * The score of the game when the last move was implemented. This is used to figure out when the last move sent to
     * the {@link #mGameController} has been applied and a new Tetromino has appeared.
     */
    protected int mPrevScore;

    /**
     * The {@link GameController} where the moves will be sent.
     */
    protected GameController mGameController;

    public GUIBrain(GameProcessor gameProcessor, GameController gameController, boolean fastMode) {
        super(gameProcessor, fastMode);
        mGameController = gameController;
        mPrevScore = -1;
    }

    /**
     * If the game is not over and a new Tetromino has appeared, get the next set of optimal moves and send it to the
     * {@link #mGameController}. If the game is over, update the statistics of the {@link #mCurOrganism} with the
     * current game.
     *
     * @param deltaTime The amount of time that has passed since the last time this function was called in seconds.
     */
    @Override
    public void update(double deltaTime) {
        switch (mGameProcessor.getmGameState()) {
            case PLAYING:
                if (mPrevScore != mGameProcessor.getmScore()) {
                    // update the score when the last move was applied
                    mPrevScore = mGameProcessor.getmScore();
                    // if the last move has been applied, meaning a new tetromino has spawned, get a new set of optimal moves
                    ArrayList<ControllerKeys> moves = getBestMove(mGameProcessor.getmGrid(), mGameProcessor.getmCurTetromino());
                    // send moves to the game controller
                    for (ControllerKeys move : moves) {
                        mGameController.keyPressed(move);
                    }
                }
                break;
            case OVER:
                // update the statistics of the organism
                mCurOrganism.setmMaxScore(mGameProcessor.getmScore());
                mCurOrganism.setmMaxLevel(mGameProcessor.getmLevel());
                mCurOrganism.setmMaxLinesCleared(mGameProcessor.getmNumLinesCleared());
                break;
        }
    }

    public GameController getmGameController() {
        return mGameController;
    }
}
