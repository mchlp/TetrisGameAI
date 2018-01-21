/*
 * Michael Pu
 * TetrisGameAI - RandomBlueprintGenerator
 * ICS3U1 - Mr.Radulovic
 * January 18, 2018
 */

package backend;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Generates a sequence for the Tetrominos to appear. Follows the algorithm set up by Blue Planet Software, Inc. It uses
 * a "bag" system to ensure equal distribution among the {@link TetrominoBlueprint}s. A random permutation of the {@link
 * TetrominoBlueprint}s is generated. The first {@link TetrominoBlueprint}s in the permutation is returned each time.
 * When the permutation is empty, a new permutation is generated.
 */
public class RandomBlueprintGenerator {

    /**
     * The iterator for accessing the next Tetromino in the permutation
     */
    private Iterator<TetrominoBlueprint> mBagIterator;

    public RandomBlueprintGenerator() {
        generateNewIterator();
    }

    /**
     * Generates a new iterator for a random permutation of the {@link TetrominoBlueprint}s.
     */
    private void generateNewIterator() {
        // Creates a list of the Tetrominos
        List<TetrominoBlueprint> newBag = Arrays.asList(TetrominoBlueprint.values());
        // Shuffles the list to create a random permutation
        Collections.shuffle(newBag);
        // Create an iterator for the permutation
        mBagIterator = newBag.iterator();
    }

    /**
     * @return The next {@link TetrominoBlueprint} in permutation.
     */
    public TetrominoBlueprint getNextTetromino() {
        if (!mBagIterator.hasNext()) {
            // if the permutation is empty, generate a new one
            generateNewIterator();
        }
        return mBagIterator.next();
    }

}
