/*
 * Michael Pu
 * TetrisGameAI - Utilities
 * ICS3U1 - Mr. Radulovic
 * December 28, 2017
 */

package backend;

/**
 * Class for performing class independent actions such as loading resources.
 */
public final class Utilities {

    /**
     * Directory of resource main.res.audio files.
     */
    public static final String AUDIO_DIRECTORY = "/audio/";

    /**
     * Path to the background music file.
     */
    public static final String AUDIO_BACKGROUND_MUSIC = AUDIO_DIRECTORY + "tetrisSoundtrack.mp3";

    /**
     * @param resource The name of the resource.
     * @return A string representing the URL of the resource. <code>null</code> if no resource matching that name is
     * found.
     */
    public static String getResourceAsURLString(String resource) {
        return Utilities.class.getResource(resource).toString();
    }

}
