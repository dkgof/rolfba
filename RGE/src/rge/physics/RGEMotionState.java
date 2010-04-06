
package rge.physics;

import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;
import javax.vecmath.Quat4f;
import rge.math.Quaternion;
import rge.math.Vector3;
import rge.nodes.Node;

/**
 *
 * @author Rolf
 */
public class RGEMotionState implements MotionState {
    private final Node node;
    private final Transform initialPosition;

    public RGEMotionState(Node node, Transform initialPosition) {
        this.node = node;
        this.initialPosition = initialPosition;
    }

    @Override
    public Transform getWorldTransform(Transform out) {
        out.set(initialPosition);

        return out;
    }

    @Override
    public void setWorldTransform(Transform worldTrans) {
        node.setPosition(worldTrans.origin.x, worldTrans.origin.y, worldTrans.origin.z);
        Quat4f rot = worldTrans.getRotation(new Quat4f());
        node.setRotation(new Quaternion(rot.w, rot.x, rot.y, rot.z));
    }

}
