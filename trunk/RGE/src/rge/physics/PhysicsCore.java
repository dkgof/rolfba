package rge.physics;

import rge.nodes.Node;

/**
 *
 * @author Rolf
 */
public class PhysicsCore {

    private static PhysicsCore singleton;

    private PhysicsCore() {
    }

    public void step(double deltaTime) {
    }

    public static synchronized PhysicsCore singleton() {
        if (singleton == null) {
            singleton = new PhysicsCore();
        }

        return singleton;
    }
}
