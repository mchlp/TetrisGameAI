/*
 * Michael Pu
 * TetrisGameAI - GameState
 * ICS3U1 - Mr.Radulovic
 * January 18, 2018
 */

package backend;

import javafx.scene.paint.Color;

/**
 * The possible states for a game.
 */
public enum GameState {

    PAUSED("Paused", Color.rgb(255, 51, 51)),
    PLAYING("Playing", Color.rgb(51, 204, 0)),
    OVER("Game Over!", Color.rgb(255, 51, 51)),
    LOADING("Loading...", Color.rgb(255, 207, 61));

    /**
     * Human-readable format of the state.
     */
    public final String message;

    /**
     * Colour associated with the state.
     */
    public final Color colour;

    GameState(String message, Color colour) {
        this.message = message;
        this.colour = colour;
    }
}
