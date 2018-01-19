/*
 * Michael Pu
 * TetrisGameAI - Utilities
 * ICS3U1 - Mr. Radulovic
 * December 28, 2017
 */

package backend;

import javafx.scene.image.Image;

/**
 * Class for performing class independent actions such as loading resources.
 */
public final class Utilities {

    /**
     * Directory of resource audio files.
     */
    public static final String AUDIO_DIRECTORY = "/audio/";

    /**
     * Directory of the resource image files.
     */
    public static final String IMAGES_DIRECTORY = "/images/";

    /**
     * Path to the background music file.
     */
    public static final String AUDIO_BACKGROUND_MUSIC = AUDIO_DIRECTORY + "tetrisSoundtrack.mp3";

    /**
     * Retrieves a string representing the URL of a file by the name of the file.
     *
     * @param resource The name of the resource.
     * @return A string representing the URL of the resource. <code>null</code> if no resource matching that name is
     * found.
     */
    public static String getResourceAsURLString(String resource) {
        return Utilities.class.getResource(resource).toString();
    }

    /**
     * Retrieves an image by the name of the file containing the image.
     *
     * @param imageName Name of image file.
     * @return {@link Image} that was in the image file.
     */
    public static Image getImage(String imageName) {
        return new Image(Utilities.getResourceAsURLString(IMAGES_DIRECTORY + imageName));
    }
}
