
package rge.animators;

import rge.physics.PhysicsCore;

/**
 *
 * @author Rolf
 */
public class PhysicsAnimator extends Animator {
    public PhysicsAnimator(int fps) {
        super(fps);
    }

    @Override
    protected void animate(double deltaTime) {
        PhysicsCore.singleton().step(deltaTime);
    }

}
