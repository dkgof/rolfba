
package rge.animators;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import rge.RGEApplication;
import rge.RGEDisplay;

/**
 * DisplayAnimator runs the rendering at a fixed framerate
 * @author Rolf
 */
public class DisplayAnimator extends Animator {
    private RGEApplication app;

    private boolean needToCreateDisplay;
    private final int width;
    private final int height;
    private final int colorDepth;
    private final boolean fullscreen;

    /**
     * Create a new display animator rendering with the given parameters
     * @param app the application to render
     * @param width the width to use for the opengl display
     * @param height the height to use for the opengl display
     * @param colorDepth the color depth to use for the opengl display
     * @param fullscreen should we render to fullscreen
     * @param fps the framerate to render at
     */
    public DisplayAnimator(RGEApplication app, int width, int height, int colorDepth, boolean fullscreen, double fps) {
        super(fps);
        this.app = app;
        needToCreateDisplay = true;
        this.width = width;
        this.height = height;
        this.colorDepth = colorDepth;
        this.fullscreen = fullscreen;
    }

    @Override
    protected void animate() {
        if(needToCreateDisplay) {
            RGEDisplay.createDisplay(width, height, colorDepth, fullscreen);
            needToCreateDisplay = false;
        }

        //Clear color and depth buffer prior drawing
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        //Reset modelview matrix
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();

        //Render the app
        app.display();

        //Update opengl
        Display.update();

        //Close if requested
        if (Display.isCloseRequested()) {
            Display.destroy();
            System.exit(0);
        }
    }
}
