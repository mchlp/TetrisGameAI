/*
 * Michael Pu
 * TetrisGameAI - RandomBlueprintGenerator
 * ICS3U1 - Mr.Radulovic
 * December 28, 2017
 */

package backend;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class RandomBlueprintGenerator {

    private Iterator<TetrominoBlueprint> mBagIterator;


    public RandomBlueprintGenerator() {
        generateNewIterator();
    }

    private void generateNewIterator() {
        List<TetrominoBlueprint> newBag = Arrays.asList(TetrominoBlueprint.values());
        Collections.shuffle(newBag);
        mBagIterator = newBag.iterator();
    }

    public TetrominoBlueprint getNextTetromino() {
        if (!mBagIterator.hasNext()) {
            generateNewIterator();
        }
        return mBagIterator.next();
    }

}
