/*
 * Michael Pu
 * TetrisGameAI - GameMOde
 * ICS3U1 - Mr. Radulovic
 * December 25, 2017
 */

package frontend.common;

import javafx.scene.paint.Color;

public enum GameMode {

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
