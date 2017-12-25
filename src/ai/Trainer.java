/*
 * Michael Pu
 * TetrisGameAI - Trainer
 * ICS3U1 - Mr. Radulovic
 * December 25, 2017
 */

package ai;

import backend.ControllerKeys;
import backend.Updatable;
import frontend.common.Cell;
import frontend.common.GameArea;
import frontend.common.GameController;
import frontend.common.Tetromino;

import java.util.ArrayList;

public class Trainer implements Updatable {

    private GameArea mGameArea;
    private GameController mGameController;
    private Population mPopulation;
    private int mCurTrainOrganismIndex;
    private Organism mCurTrainOrganism;

    public Trainer(GameArea gameArea, GameController gameController, Population population) {
        mGameArea = gameArea;
        mGameController = gameController;
        mPopulation = population;
        mCurTrainOrganismIndex = 0;
        mCurTrainOrganism = population.getOrganism(0);
    }

    @Override
    public void update(double deltaTime) {
        switch (mGameArea.getmGameState()) {
            case PLAYING:
                int num = (int) (Math.random()*10);
                int rotate = (int) (Math.random()*4);
                while (rotate-- > 0) {
                    mGameController.keyPressed(ControllerKeys.ROTATE);
                }
                if (Math.random() >= 0.5) {
                    while (num-- > 0) {
                        mGameController.keyPressed(ControllerKeys.LEFT);
                    }
                } else {
                    while (num-- > 0) {
                        mGameController.keyPressed(ControllerKeys.RIGHT);
                    }
                }
                mGameController.keyPressed(ControllerKeys.DROP);
                break;
            case OVER:
                mGameController.keyPressed(ControllerKeys.RESTART);
                break;
        }
    }

    private ArrayList<ControllerKeys> getBestMove(Cell[][] grid, Tetromino curTetromino) {
        ArrayList<ControllerKeys> bestMoves = new ArrayList<>();

        for (int numRotations = 0; numRotations < 4; numRotations++) {
            
        }

        bestMoves.clear();
    }

    private int getFullLines(Cell[][] grid) {
        int numFullLines = 0;
        for (int i=0; i<grid[0].length; i++) {
            boolean lineFull = true;
            for (int j=0; j<grid.length; j++) {
                if (!grid[j][i].ismIsFilled()) {
                    lineFull = false;
                    break;
                }
            }
            if (lineFull) {
                numFullLines++;
            }
        }
        return numFullLines;
    }

    private int getTotalHeight(Cell[][] grid) {
        int sumHeight = 0;
        for (int i=0; i<grid.length; i++) {
            for (int j=0; j<grid[0].length; j++) {
                if (grid[i][j].ismIsFilled()) {
                    sumHeight += grid[0].length-j;
                    break;
                }
            }
        }
        return sumHeight;
    }

    private int getMaxHeight(Cell[][] grid) {
        int maxHeight = 0;
        for (int i=0; i<grid.length; i++) {
            for (int j=0; j<grid[0].length; j++) {
                if (grid[i][j].ismIsFilled()) {
                    int height = grid[0].length-j;
                    if (height > maxHeight) {
                        maxHeight = height;
                    }
                    break;
                }
            }
        }
        return maxHeight;
    }

    private int getRelativeHeight(Cell[][] grid) {
        int maxHeight = 0;
        int minHeight = Integer.MAX_VALUE;
        for (int i=0; i<grid.length; i++) {
            for (int j=0; j<grid[0].length; j++) {
                if (grid[i][j].ismIsFilled()) {
                    int height = grid[0].length-j;
                    if (height > maxHeight) {
                        maxHeight = height;
                    }
                    if (height < minHeight) {
                        minHeight = height;
                    }
                    break;
                }
            }
        }
        return maxHeight-minHeight;
    }

    private int getRoughness(Cell[][] grid) {
        int prevHeight = 0;
        int sumAbsDiff = 0;
        for (int i=0; i<grid.length; i++) {
            for (int j=0; j<grid[0].length; j++) {
                if (grid[i][j].ismIsFilled()) {
                    int height = grid[0].length-j;
                    if (!(i == 0)) {
                        sumAbsDiff += Math.abs(height-prevHeight);
                    }
                    prevHeight = height;
                    break;
                }
            }
        }
        return sumAbsDiff;
    }

    private int getHoles(Cell[][] grid) {
        int numHoles = 0;
        for (int i=0; i<grid.length; i++) {
            boolean foundTop = false;
            for (int j = 0; j < grid[0].length; j++) {
                if (foundTop) {
                    if (!grid[i][j].ismIsFilled()) {
                        numHoles++;
                    }
                } else {
                    if (grid[i][j].ismIsFilled()) {
                        foundTop = true;
                    }
                }
            }
        }
        return numHoles;
    }

    public Organism getmCurTrainOrganism() {
        return mCurTrainOrganism;
    }
}
