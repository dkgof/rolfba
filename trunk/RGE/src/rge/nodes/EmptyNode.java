
package rge.nodes;

/**
 *
 * @author Rolf
 */
public class EmptyNode extends Node {

    @Override
    public void render() {
    }

    @Override
    public void update(double deltaTime) {
    }

    @Override
    public void createPhysics(float mass) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
