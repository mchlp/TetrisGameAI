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
    private int mCurOrganismIndex;
    private Organism mCurOrganism;
    private int mPrevScore;

    public Trainer(GameArea gameArea, GameController gameController, Population population) {
        mGameArea = gameArea;
        mGameController = gameController;
        mPopulation = population;
        mPrevScore = -1;
        //goToFirstOrganism();
        mCurOrganismIndex = 48;
        prepareNextOrganism();
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

                if (mCurOrganismIndex == mPopulation.getNumOrganisms()-1) {
                    mPopulation.evolve();
                    goToFirstOrganism();
                } else {
                    prepareNextOrganism();
                }
                mGameController.keyPressed(ControllerKeys.RESTART);
                break;
        }
    }

    private void goToFirstOrganism() {
        mCurOrganismIndex = -1;
        prepareNextOrganism();
    }

    private void prepareNextOrganism() {
        //TODO: CHANGE TO 0
        if (mCurOrganismIndex >= 100) {
            mCurOrganism.printFitness();
        }
        mCurOrganismIndex++;
        mCurOrganism = mPopulation.getOrganism(mCurOrganismIndex);
        mCurOrganism.printGenes();
    }

    private ArrayList<ControllerKeys> getBestMove(GameGrid grid, Tetromino curTetromino, Genome genome) {

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
                testGrid.applyTetromino(testTetromino);

                double curRating = getRating(testGrid, genome);

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

    public Organism getmCurOrganism() {
        return mCurOrganism;
    }

    public Population getmPopulation() {
        return mPopulation;
    }

    public int getmCurOrganismIndex() {
        return mCurOrganismIndex;
    }
}
