
package rge.animators;

import rge.RGEApplication;

/**
 *
 * @author Rolf
 */
public class UpdateAnimator extends Animator {
    private RGEApplication app;

    public UpdateAnimator(RGEApplication app, int fps) {
        super(fps);
        this.app = app;
    }

    @Override
    protected void animate(double deltaTime) {
        app.update(deltaTime);
    }

}
