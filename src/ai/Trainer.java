/*
 * Michael Pu
 * TetrisGameAI - Trainer
 * ICS3U1 - Mr. Radulovic
 * December 25, 2017
 */

package ai;

import backend.ControllerKeys;
import backend.Updatable;
import frontend.common.*;

import java.util.ArrayList;

public class Trainer implements Updatable {

    private static final int GAME_OVER_RATING_PENALTY = 500;

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

    private ArrayList<ControllerKeys> getBestMove(GameGrid grid, Tetromino curTetromino) {
        ArrayList<ControllerKeys> bestMoves = new ArrayList<>();

        for (int numRotations = 0; numRotations < 4; numRotations++) {
            for (int numTranslate = -(grid.getmWidth()/2); numTranslate <= grid.getmWidth()/2; numTranslate++) {


            }
        }

        bestMoves.clear();

        return bestMoves;
    }

    private double getRating(GameGrid grid, Genome genome) {
        double rating = 0;

        rating += genome.getGeneValue(Genes.LINES_CLEARED) * getNumFullLines(grid);
        rating += genome.getGeneValue(Genes.NUM_HOLES) * getNumHoles(grid);
        rating += genome.getGeneValue(Genes.ROUGHNESS) * getRoughness(grid);
        rating += genome.getGeneValue(Genes.RELATIVE_HEIGHT) * getRelativeHeight(grid);
        rating += genome.getGeneValue(Genes.TOTAL_HEIGHT) * getTotalHeight(grid);
        rating += genome.getGeneValue(Genes.MAX_HEIGHT) * getMaxHeight(grid);

        if (getGameOver(grid)) {
            rating -= GAME_OVER_RATING_PENALTY;
        }
        return rating;
    }

    private boolean getGameOver(GameGrid grid) {
        return grid.checkGameOver();
    }

    private int getNumFullLines(GameGrid grid) {
        int numFullLines = 0;
        for (int i=0; i<grid.getmHeight(); i++) {
            boolean lineFull = true;
            for (int j=0; j<grid.getmWidth(); j++) {
                if (!grid.isFilled(j, i)) {
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

    private int getTotalHeight(GameGrid grid) {
        int sumHeight = 0;
        for (int i=0; i<grid.getmWidth(); i++) {
            for (int j=0; j<grid.getmHeight(); j++) {
                if (grid.isFilled(i, j)) {
                    sumHeight += grid.getmHeight()-j;
                    break;
                }
            }
        }
        return sumHeight;
    }

    private int getMaxHeight(GameGrid grid) {
        int maxHeight = 0;
        for (int i=0; i<grid.getmWidth(); i++) {
            for (int j=0; j<grid.getmHeight(); j++) {
                if (grid.isFilled(i, j)) {
                    int height = grid.getmHeight()-j;
                    if (height > maxHeight) {
                        maxHeight = height;
                    }
                    break;
                }
            }
        }
        return maxHeight;
    }

    private int getRelativeHeight(GameGrid grid) {
        int maxHeight = 0;
        int minHeight = Integer.MAX_VALUE;
        for (int i=0; i<grid.getmWidth(); i++) {
            for (int j=0; j<grid.getmHeight(); j++) {
                if (grid.isFilled(i, j)) {
                    int height = grid.getmHeight()-j;
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

    private int getRoughness(GameGrid grid) {
        int prevHeight = 0;
        int sumAbsDiff = 0;
        for (int i=0; i<grid.getmWidth(); i++) {
            for (int j=0; j<grid.getmHeight(); j++) {
                if (grid.isFilled(i, j)) {
                    int height = grid.getmHeight()-j;
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

    private int getNumHoles(GameGrid grid) {
        int numHoles = 0;
        for (int i=0; i<grid.getmWidth(); i++) {
            boolean foundTop = false;
            for (int j = 0; j < grid.getmHeight(); j++) {
                if (foundTop) {
                    if (!grid.isFilled(i, j)) {
                        numHoles++;
                    }
                } else {
                    if (grid.isFilled(i, j)) {
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
