/*
 * Michael Pu
 * TetrisGameAI - Updatable
 * ICS3U1 - Mr.Radulovic
 * January 13, 2018
 */

package backend;

/**
 * This interface is implemented by objects that should be updated every frame, when the JavaFX Application is updated.
 * It allows all objects that need to be updated to be stored in an ArrayList and the <code>update</code> method in each
 * object in the list will be called each frame.
 */
public interface Updatable {
    void update(double deltaTime);
}
