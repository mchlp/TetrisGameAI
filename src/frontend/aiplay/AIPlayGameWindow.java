/*
 * Michael Pu
 * TetrisGameAI - AIPlayGameWindow
 * ICS3U1 - Mr. Radulovic
 * December 29, 2017
 */

package frontend.aiplay;

import ai.Organism;
import ai.Watcher;
import backend.GameMode;
import frontend.base.ThreePanelGameWindow;
import frontend.common.GameArea;
import frontend.common.GameController;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class AIPlayGameWindow extends ThreePanelGameWindow {

    private Watcher mWatcher;
    private Organism mOrganism;
    private GameArea mAIGameArea;
    private GameArea mPlayerGameArea;

    public AIPlayGameWindow(Stage stage, Organism organism, double height, double width) {
        super(stage, height, width, GameMode.AI_PLAY);

        mWindowTitle = "Tetris Game - Play Against AI Version";
        mOrganism = organism;

        // left side of screen
        Rectangle leftGameAreaBackground = new Rectangle(mCanvasWidth + 10, mCanvasHeight + 10, GAME_OUTLINE_COLOUR);
        mLeftGamePane.getChildren().add(leftGameAreaBackground);

        mAIGameArea = new GameArea(mBackgroundMusic, mCanvasWidth, mCanvasHeight, GAME_BACKGROUND_COLOUR, mGameMode);
        mLeftGamePane.getChildren().add(mAIGameArea);
        mLeftGameController = new GameController(mAIGameArea);
        mUpdateItems.add(mAIGameArea);

        mWatcher = new Watcher(mAIGameArea.getmGameProcessor(), mLeftGameController, mOrganism, false);
        mUpdateItems.add(mWatcher);

        // right side of screen
        Rectangle rightGameAreaBackground = new Rectangle(mCanvasWidth + 10, mCanvasHeight + 10, GAME_OUTLINE_COLOUR);
        mRightGamePane.getChildren().add(rightGameAreaBackground);

        mPlayerGameArea = new GameArea(mBackgroundMusic, mCanvasWidth, mCanvasHeight, GAME_BACKGROUND_COLOUR, mGameMode);
        mRightGamePane.getChildren().add(mPlayerGameArea);
        mRightGameController = new GameController(mPlayerGameArea);
        mUpdateItems.add(mPlayerGameArea);

        // side bar in the middle
        AIPlaySidebar aiPlaySidebar = new AIPlaySidebar(this, mAIGameArea, mPlayerGameArea, mLeftGameController, mRightGameController, DEFAULT_MARGINS, mSideBarHeight, mSideBarWidth, GAME_BACKGROUND_COLOUR);
        setCenter(aiPlaySidebar);
        mUpdateItems.add(aiPlaySidebar);
    }
}
