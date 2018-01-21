/*
 * Michael Pu
 * TetrisGameAI - GameMode
 * ICS3U1 - Mr.Radulovic
 * January 18, 2018
 */

package backend;

import javafx.scene.paint.Color;

/**
 * The possible modes for the game. These are the different windows that can opened.
 */
public enum GameMode {

    MAIN_MENU("Main Menu", Color.GREY),
    PLAYER("Human Player", Color.rgb(255, 176, 63)),
    AI_TRAINER("AI Training", Color.rgb(67, 133, 255)),
    AI_FAST_TRAINER("AI Fast Training", Color.rgb(39, 152, 66)),
    AI_WATCHER("AI Watching", Color.rgb(185, 69, 255)),
    AI_PLAY("Play Against AI", Color.rgb(220, 59, 63));

    /**
     * Human-readable form of the mode.
     */
    public final String message;

    /**
     * Colour associated with the mode.
     */
    public final Color colour;

    GameMode(String message, Color colour) {
        this.message = message;
        this.colour = colour;
    }
}
