
package rge.animators;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import rge.RGEApplication;
import rge.RGEDisplay;

/**
 *
 * @author Rolf
 */
public class DisplayAnimator extends Animator {
    private RGEApplication app;

    private boolean needToCreateDisplay;
    private final int width;
    private final int height;
    private final int colorDepth;
    private final boolean fullscreen;

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

        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();

        app.display();

        Display.update();

        if (Display.isCloseRequested()) {
            Display.destroy();
            System.exit(0);
        }
    }
}
