package rge.physics;

import com.bulletphysics.collision.broadphase.AxisSweep3;
import com.bulletphysics.collision.broadphase.BroadphaseInterface;
import com.bulletphysics.collision.dispatch.CollisionConfiguration;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.demos.opengl.GLDebugDrawer;
import com.bulletphysics.demos.opengl.LWJGL;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.dynamics.constraintsolver.ConstraintSolver;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import com.bulletphysics.extras.gimpact.GImpactCollisionAlgorithm;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;
import javax.vecmath.Vector3f;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author Rolf
 */
public class PhysicsCore {

    private static PhysicsCore singleton;
    private DiscreteDynamicsWorld dynamicsWorld;

    private PhysicsCore() {
        CollisionConfiguration config = new DefaultCollisionConfiguration();

        CollisionDispatcher dispatcher = new CollisionDispatcher(config);
        GImpactCollisionAlgorithm.registerAlgorithm(dispatcher);

        Vector3f worldAabbMin = new Vector3f(-10000, -10000, -10000);
        Vector3f worldAabbMax = new Vector3f(10000, 10000, 10000);
        BroadphaseInterface broadphase = new AxisSweep3(worldAabbMin, worldAabbMax);

        ConstraintSolver solver = new SequentialImpulseConstraintSolver();

        dynamicsWorld = new DiscreteDynamicsWorld(dispatcher, broadphase, solver, config);
        dynamicsWorld.setGravity(new Vector3f(0f, -10f, 0f));
    }

    public void step(double deltaTime) {
        dynamicsWorld.stepSimulation((float) deltaTime);
    }

    public static synchronized PhysicsCore singleton() {
        if (singleton == null) {
            singleton = new PhysicsCore();
        }

        return singleton;
    }

    public RigidBody localCreateRigidBody(float mass, Transform startTransform, CollisionShape shape) {
        boolean isDynamic = (mass != 0f);

        Vector3f localInertia = new Vector3f(0f, 0f, 0f);
        if (isDynamic) {
            shape.calculateLocalInertia(mass, localInertia);
        }

        DefaultMotionState myMotionState = new DefaultMotionState(startTransform);

        RigidBodyConstructionInfo cInfo = new RigidBodyConstructionInfo(mass, myMotionState, shape, localInertia);

        RigidBody body = new RigidBody(cInfo);

        dynamicsWorld.addRigidBody(body);

        return body;
    }
}
