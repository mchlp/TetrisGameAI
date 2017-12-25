/*
 * Michael Pu
 * TetrisGameAI - GameState
 * ICS3U1 - Mr. Radulovic
 * December 24, 2017
 */

package frontend.common;

import javafx.scene.paint.Color;

public enum GameState {

    PAUSED("Paused", Color.rgb(255, 51, 51)),
    PLAYING("Playing", Color.rgb(51, 204, 0)),
    OVER("Game Over!", Color.rgb(255, 51, 51));

    public final String message;
    public final Color colour;

    GameState(String message, Color colour) {
        this.message = message;
        this.colour = colour;
    }
}