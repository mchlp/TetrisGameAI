/*
 * Michael Pu
 * TetrisGameAI - Utilities
 * ICS3U1 - Mr. Radulovic
 * December 28, 2017
 */

package backend;

import java.io.InputStream;
import java.net.URL;

public class Utilities {

    public static final String AUDIO_DIRECTORY = "/audio/";

    public static final String AUDIO_BACKGROUND_MUSIC = AUDIO_DIRECTORY + "tetrisSoundtrack.mp3";

    public static URL getResourceAsURL(String resource) {
        return Utilities.class.getResource(resource);
    }

    public static String getResourceAsURLString(String resource) {
        return Utilities.class.getResource(resource).toString();
    }

    public static InputStream getResourceAsInputStream(String resource) {
        return Utilities.class.getResourceAsStream(resource);
    }

}
