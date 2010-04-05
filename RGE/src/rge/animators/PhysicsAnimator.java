
package rge.animators;

import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import rge.physics.PhysicsCore;

/**
 *
 * @author Rolf
 */
public class PhysicsAnimator extends Animator {
    private DiscreteDynamicsWorld dynamicsWorld;

    public PhysicsAnimator(int fps) {
        super(fps);
    }

    @Override
    protected void animate(double deltaTime) {
        PhysicsCore.singleton().step(deltaTime);
    }

}
