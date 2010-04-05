
package rge;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

/**
 * RGEDisplay controls the opengl display
 * @author Rolf
 */
public class RGEDisplay {
    private static RGEDisplay singleton;

    private DisplayMode chosenMode;

    private RGEDisplay(int width, int height, int colorDepth, boolean fullscreen) {
        try {
            for (DisplayMode mode : Display.getAvailableDisplayModes()) {
                if(mode.getWidth() == width && mode.getHeight() == height && mode.getBitsPerPixel() == colorDepth) {
                    if(fullscreen) {
                        if(!mode.isFullscreenCapable()) {
                            continue;
                        }
                    }
                    chosenMode = mode;
                    break;
                }
            }
        } catch (LWJGLException ex) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Error determining displaymode", ex);
            System.exit(-1);
        }

        if(chosenMode == null) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Unable to find requested displaymode");
            System.exit(-1);
        }

        try {
            if(fullscreen) {
                Display.setDisplayModeAndFullscreen(chosenMode);
            }
            else {
                Display.setDisplayMode(chosenMode);
            }
            Display.setTitle("RGE Window");
            Display.create();
        }
        catch(LWJGLException ex) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Unable to create display", ex);
            System.exit(-1);
        }
    }

    /**
     * Get the singleton RGEDisplay
     * @return the singleton RGEDisplay
     */
    public static RGEDisplay getDisplay() {
        if(singleton == null) {
            throw new IllegalStateException("Display has not yet been created");
        }
        return singleton;
    }

    /**
     * Create the display
     * @param width requested width
     * @param height requested height
     * @param colorDepth requested bits per pixel
     * @param fullscreen fullscreen true/false
     */
    public static void createDisplay(int width, int height, int colorDepth, boolean fullscreen) {
        if(singleton != null) {
            throw new IllegalStateException("Display has already been created");
        }

        singleton = new RGEDisplay(width, height, colorDepth, fullscreen);
    }

    /**
     * Get the height if the display
     * @return the height of the display
     */
    public int getHeight() {
        return Display.getDisplayMode().getHeight();
    }

    /**
     * Get the width if the display
     * @return the width of the display
     */
    public int getWidth() {
        return Display.getDisplayMode().getWidth();
    }

    /**
     * Get the aspect of the display
     * @return the aspect of the display
     */
    public double getAspect() {
        return getWidth() / ((double)(getHeight()));
    }
}
