/*
 * Michael Pu
 * TetrisGameAI - NextTetrominoBox
 * ICS3U1 - Mr. Radulovic
 * December 24, 2017
 */

package frontend;

import backend.Updatable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
            mGc.setFill(mGameArea.CELL_OUTLINE_COLOUR);
            mGc.fillRoundRect(x, y, width, height, 5, 5);
            mGc.setFill(colour);
            mGc.fillRoundRect(x + mGameArea.CELL_OUTLINE_WIDTH, y + mGameArea.CELL_OUTLINE_WIDTH,
                    width - (mGameArea.CELL_OUTLINE_WIDTH * 2), height - (mGameArea.CELL_OUTLINE_WIDTH * 2),
                    5, 5);
    }

    @Override
    public void update(double deltaTime) {
        mGc.setFill(mBgColour);
        mGc.fillRect(0,0,mDimension, mDimension);

        Tetromino nextTetromino = mGameArea.getmNextTetromino();
        Color colour = nextTetromino.getmColour();
        int[][] body = nextTetromino.getmBody();

        int bodyActualWidth = 0;
        int bodyActualLength = 0;

        for (int i=0; i<body.length; i++) {
            for (int j=0; j<body[i].length; j++) {
                if (body[i][j] == 1) {
                    bodyActualLength++;
                    break;
                }
            }
        }

        for (int i=0; i<body[0].length; i++) {
            for (int j=0; j<body.length; j++) {
                if (body[j][i] == 1) {
                    bodyActualWidth++;
                    break;
                }
            }
        }

        double cellWidth = mGameArea.getmCellWidth();
        double cellHeight = mGameArea.getmCellHeight();
        double xOffset = (mDimension-(bodyActualWidth * cellWidth))/2;
        double yOffset = (mDimension-(bodyActualLength * cellHeight))/2;

        for (int i=0; i<body[0].length; i++) {
            for (int j=0; j<body.length; j++) {
                if (body[j][i] == 1) {
                    drawCell(xOffset + i*cellWidth, yOffset + j*cellHeight, cellWidth, cellHeight, colour);
                }
            }
        }

    }
}
