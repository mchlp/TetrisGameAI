/*
 * Michael Pu
 * TetrisGameAI - AITrainSidebar
 * ICS3U1 - Mr. Radulovic
 * December 25, 2017
 */

package frontend.aiTrain;

import ai.Trainer;
import frontend.base.Sidebar;
import frontend.common.GameArea;

public class AITrainSidebar extends Sidebar {
    public AITrainSidebar(GameArea gameArea, Trainer trainer, double margins, double sideBarHeight, double sideBarWidth) {
        super(gameArea, margins, sideBarHeight, sideBarWidth);

        AITrainStatsBox aiTrainStatsBox = new AITrainStatsBox(gameArea, trainer);
        getChildren().add(aiTrainStatsBox);
        mUpdateItems.add(aiTrainStatsBox);
    }
}
