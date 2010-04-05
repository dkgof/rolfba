
package rge.importing;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rolf
 */
public class Face {
    private List<FacePoint> points;

    public Face() {
        points = new ArrayList<FacePoint>();
    }

    public void addFacePoint(FacePoint fp) {
        points.add(fp);
    }

    /**
     * @return the points
     */
    public List<FacePoint> getPoints() {
        return points;
    }

    public boolean isTriangle() {
        return points.size() == 3;
    }
}
