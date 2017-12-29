/*
 * Michael Pu
 * TetrisGameAI - GameMode
 * ICS3U1 - Mr.Radulovic
 * December 28, 2017
 */

package backend;

import javafx.scene.paint.Color;

public enum GameMode {

    MAIN_MENU("Main Menu", Color.GREY),
    PLAYER("Human Player", Color.rgb(255, 176, 63)),
    AI_TRAINER("AI Training", Color.rgb(67, 133, 255)),
    AI_FAST_TRAINER("AI Fast Training", Color.rgb(39, 152, 66)),
    AI_WATCHER("AI Watching", Color.rgb(185, 69, 255));

    public final String message;
    public final Color colour;

    GameMode(String message, Color colour) {
        this.message = message;
        this.colour = colour;
    }
}
