/*
 * Michael Pu
 * TetrisGameAI - GameArea
 * ICS3U1 - Mr. Radulovic
 * December 23, 2017
 */

package frontend;

import backend.TetrominoBlueprint;
import backend.Updatable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;

public class GameArea extends Canvas implements Updatable {

    private static final int NUM_ROWS = 20;
    private static final int NUM_COLS = 10;

    private static final Color LINE_COLOUR = Color.GREY;
    private static final double LINE_WIDTH = 0.5;

    private static final Color CELL_OUTLINE_COLOUR = Color.BLACK;
    private static final double CELL_OUTLINE_WIDTH = 3;

    private Color bgColour;
    private GraphicsContext gc;
    private double width;
    private double height;
    private double cellWidth;
    private double cellHeight;
    private Cell[][] grid;
    private Tetromino curTetromino;

    public GameArea(double width, double height, Color bgColour) {
        super(width, height);
        this.height = height;
        this.width = width;
        this.bgColour = bgColour;
        cellHeight = this.height /NUM_ROWS;
        cellWidth = this.width /NUM_COLS;
        gc = getGraphicsContext2D();
        grid = new Cell[NUM_COLS][NUM_ROWS];
        for (int i=0; i<NUM_COLS; i++) {
            for (int j=0; j<NUM_ROWS; j++) {
                grid[i][j] = new Cell();
            }
        }
        curTetromino = new Tetromino(TetrominoBlueprint.S, NUM_COLS);
    }

    public void moveLeft() {
        System.out.println("LEFT");
    }

    public void moveRight() {
        System.out.println("RIGHT");
    }

    public void moveDown() {
        System.out.println("DOWN");
    }

    public void rotate() {
        System.out.println("ROTATE");
    }

    public void drop() {
        System.out.println("DROP");
    }

    private void drawCell(int x, int y, Color colour) {
        gc.setFill(colour.darker());
        gc.fillRoundRect(x* cellWidth, y* cellHeight, cellWidth, cellHeight, 5, 5);
        gc.setFill(colour);
        gc.fillRoundRect(x* cellWidth +CELL_OUTLINE_WIDTH, y* cellHeight +CELL_OUTLINE_WIDTH,
                cellWidth -(CELL_OUTLINE_WIDTH*2), cellHeight -(CELL_OUTLINE_WIDTH*2), 5, 5);
    }

    public void update(double deltaTime) {

        gc.setFill(bgColour);
        gc.fillRect(0, 0, width, height);

        for (int i=0; i<NUM_COLS; i++) {
            for (int j=0; j<NUM_ROWS; j++) {
                if (grid[i][j].isFilled()) {
                    drawCell(i, j, grid[i][j].getColour());
                }
            }
        }

        if (curTetromino != null) {
            int[][] tBody = curTetromino.getBody();
            Point tPos = curTetromino.getCurPos();
            for (int i=0; i<tBody.length; i++) {
                for (int j=0; j<tBody[0].length; j++) {
                    if (tBody[i][j] == 1) {
                        drawCell(tPos.x+i, tPos.y+j, curTetromino.getColour());
                    }
                }
            }
        }

        /*
        gc.setStroke(LINE_COLOUR);
        gc.setLineWidth(LINE_WIDTH);

        for (int col=0; col<NUM_COLS; col++) {
            gc.strokeLine(col*cellWidth, 0,col*cellWidth, height);
        }

        for (int row=0; row<NUM_ROWS; row++) {
            gc.strokeLine(0,row*cellHeight, width, row*cellHeight);
        }*/
    }

}
