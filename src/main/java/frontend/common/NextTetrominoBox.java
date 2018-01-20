/*
 * Michael Pu
 * TetrisGameAI - NextTetrominoBox
 * ICS3U1 - Mr.Radulovic
 * January 18, 2018
 */

package frontend.common;

import backend.GameState;
import backend.Updatable;
import frontend.common.GameArea;
import backend.Tetromino;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.paint.Color;

public class NextTetrominoBox extends Canvas implements Updatable {

    private GameArea mGameArea;
    private GraphicsContext mGc;
    private Color mBgColour;
    private double mDimension;

    public NextTetrominoBox(GameArea gameArea, double dimension, Color bgColour) {
        super(dimension, dimension);
        mDimension = dimension;
        mGameArea = gameArea;
        mGc = getGraphicsContext2D();
        mBgColour = bgColour;
    }

    private void drawCell(double x, double y, double width, double height, Color colour) {
        mGc.setFill(GameArea.CELL_OUTLINE_COLOUR);
        mGc.fillRoundRect(x, y, width, height, 5, 5);
        mGc.setFill(colour);
        mGc.fillRoundRect(x + GameArea.CELL_OUTLINE_WIDTH, y + GameArea.CELL_OUTLINE_WIDTH,
                width - (GameArea.CELL_OUTLINE_WIDTH * 2), height - (GameArea.CELL_OUTLINE_WIDTH * 2),
                5, 5);
    }

    @Override
    public void update(double deltaTime) {

        setEffect(null);
        mGc.setFill(mBgColour);
        mGc.fillRect(0, 0, mDimension, mDimension);

        Tetromino nextTetromino = mGameArea.getmGameProcessor().getmNextTetromino();
        Color colour = nextTetromino.getmColour();
        int[][] body = nextTetromino.getmBody();

        int bodyActualWidth = 0;
        int bodyActualHeight = 0;
        int emptyRowsOnTop = 0;
        int emptyRowsOnLeft = 0;

        for (int i = 0; i < body.length; i++) {
            for (int j = 0; j < body[i].length; j++) {
                if (body[i][j] == 1) {
                    bodyActualHeight++;
                    break;
                }
            }
            if (bodyActualHeight == 0) {
                emptyRowsOnTop++;
            }
        }

        for (int i = 0; i < body[0].length; i++) {
            for (int j = 0; j < body.length; j++) {
                if (body[j][i] == 1) {
                    bodyActualWidth++;
                    break;
                }
            }
            if (bodyActualWidth == 0) {
                emptyRowsOnLeft++;
            }
        }

        double cellWidth = mGameArea.getmCellWidth();
        double cellHeight = mGameArea.getmCellHeight();
        double xOffset = (mDimension - (bodyActualWidth * cellWidth)) / 2;
        double yOffset = (mDimension - (bodyActualHeight * cellHeight)) / 2;

        for (int i = 0; i < body[0].length; i++) {
            for (int j = 0; j < body.length; j++) {
                if (body[j][i] == 1) {
                    drawCell(xOffset + (i - emptyRowsOnLeft) * cellWidth, yOffset + (j - emptyRowsOnTop) * cellHeight, cellWidth, cellHeight, colour);
                }
            }
        }

        if (mGameArea.getmGameProcessor().getmGameState() == GameState.PAUSED) {
            setEffect(new GaussianBlur(15));
        }

    }
}
