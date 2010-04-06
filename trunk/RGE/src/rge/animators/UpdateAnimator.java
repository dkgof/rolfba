
package rge.animators;

import rge.RGEApplication;
import rge.physics.PhysicsCore;

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
