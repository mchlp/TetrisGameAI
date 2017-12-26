/*
 * Michael Pu
 * TetrisGameAI - Brain
 * ICS3U1 - Mr. Radulovic
 * December 25, 2017
 */

package ai;

import backend.ControllerKeys;
import backend.GameBrain;
import backend.Updatable;
import frontend.common.GameArea;
import frontend.common.GameController;
import frontend.common.GameGrid;
import frontend.common.Tetromino;

import java.util.ArrayList;

public abstract class Brain {

    private static final int GAME_OVER_RATING_PENALTY = 500;

    protected Organism mCurOrganism;

    protected ArrayList<ControllerKeys> getBestMove(GameGrid grid, Tetromino curTetromino, Genome genome) {

        double highestRating = Double.NEGATIVE_INFINITY;

        ArrayList<ControllerKeys> bestMoves = new ArrayList<>();

        for (int numRotations = 0; numRotations < 4; numRotations++) {
            for (int numTranslate = -(grid.getmWidth()/2); numTranslate <= grid.getmWidth()/2; numTranslate++) {

                GameGrid testGrid = grid.clone();
                Tetromino testTetromino = curTetromino.clone();

                for (int i=0; i<numRotations; i++) {
                    testTetromino.rotate(false);
                }

                for (int i=0; i<Math.abs(numTranslate); i++) {
                    if (numTranslate < 0) {
                        testTetromino.moveLeft(false);
                    } else {
                        testTetromino.moveRight(false);
                    }
                }

                testTetromino.drop(false);

                double curRating = getRating(testGrid, genome, testTetromino);

                if (curRating > highestRating) {
                    highestRating = curRating;
                    bestMoves.clear();
                    for (int i=0; i<numRotations; i++) {
                        bestMoves.add(ControllerKeys.ROTATE);
                    }
                    for (int i=0; i<Math.abs(numTranslate); i++) {
                        if (numTranslate < 0) {
                            bestMoves.add(ControllerKeys.LEFT);
                        } else {
                            bestMoves.add(ControllerKeys.RIGHT);
                        }
                    }
                    bestMoves.add(ControllerKeys.DROP);
                }
            }
        }
        return bestMoves;
    }

    protected double getRating(GameGrid grid, Genome genome, Tetromino tetromino) {

        int beforeHoles = getNumHoles(grid);
        grid.applyTetromino(tetromino);
        int numClearRows = grid.checkCompleteRows();
        double rating = 0;

        int minHeight = getMinHeight(grid);
        int maxHeight = getMaxHeight(grid);

        rating += genome.getGeneValue(Genes.LINES_CLEARED) * numClearRows;
        rating += genome.getGeneValue(Genes.NUM_HOLES) * getNumHoles(grid);
        rating += genome.getGeneValue(Genes.INCREASED_NUMBER_OF_HOLES) * getNumHoles(grid) - beforeHoles;
        rating += genome.getGeneValue(Genes.SUM_BLOCKS_ABOVE_HOLE) * getSumBlocksAboveHole(grid);
        rating += genome.getGeneValue(Genes.ROUGHNESS) * getRoughness(grid);
        rating += genome.getGeneValue(Genes.TOTAL_HEIGHT) * getTotalHeight(grid);
        rating += genome.getGeneValue(Genes.MAX_HEIGHT) * maxHeight;
        rating += genome.getGeneValue(Genes.MIN_MAX_HEIGHT_DIFFERENCE) * (maxHeight - minHeight);

        if (getGameOver(grid)) {
            rating -= GAME_OVER_RATING_PENALTY;
        }
        return rating;
    }

    private boolean getGameOver(GameGrid grid) {
        return grid.checkGameOver();
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

    private int getSumBlocksAboveHole(GameGrid grid) {
        int sumBlocksAboveHole = 0;
        for (int i=0; i<grid.getmWidth(); i++) {
            int numHolesInCol = 0;
            for (int j=grid.getmHeight()-1; j>=0; j--) {
                if (!grid.isFilled(i, j)) {
                    numHolesInCol++;
                } else {
                    sumBlocksAboveHole += numHolesInCol;
                }
            }
        }
        return sumBlocksAboveHole;
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

    private int getMinHeight(GameGrid grid) {
        int minHeight = Integer.MAX_VALUE;
        for (int i=0; i<grid.getmWidth(); i++) {
            for (int j=0; j<grid.getmHeight(); j++) {
                if (grid.isFilled(i, j)) {
                    int height = grid.getmHeight()-j;
                    if (height < minHeight) {
                        minHeight = height;
                    }
                    break;
                }
            }
        }
        return minHeight;
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

    public Organism getmCurOrganism() {
        return mCurOrganism;
    }
}
