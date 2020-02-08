import java.util.Arrays;
import java.util.List;

/**
 * Entity that is a co-planar polygon to be hit by rays
 */
public class Polygon implements Entity {

    private static final double ANGLE_ERROR_MARGIN = (2 * Math.PI)/1000.0;

    private Color color;
    private List<Vector3D> points;
    private Vector3D planeNormal;
    private double planeConstant;

    public Polygon(Color color, Vector3D ... points){
        if(points.length < 3){
            throw new IllegalArgumentException("Polygon requires at least 3 points");
        }
        this.color = color;
        this.points = Arrays.asList(points);
        Vector3D firstSide = points[0].subtract(points[1]);
        Vector3D secondSide = points[2].subtract(points[1]);
        planeNormal = secondSide.cross(firstSide);
        planeConstant = -1 * planeNormal.dot(points[0]);
    }


    public static Polygon createRectangle(Color color, Vector3D center, Vector3D sideVector1, Vector3D sideVector2){
        return new Polygon(
                color,
                center.add(sideVector1).subtract(sideVector2),
                center.add(sideVector1).add(sideVector2),
                center.subtract(sideVector1).add(sideVector2),
                center.subtract(sideVector1).subtract(sideVector2)
        );
    }

    @Override
    public double intersect(Ray ray) {
        Vector3D origin = ray.getOrigin();
        Vector3D direction = ray.getDirection();

        double denominator = direction.dot(planeNormal);
        if(denominator == 0) return Double.MAX_VALUE;
        double intersectScalar = - (origin.dot(planeNormal) + planeConstant) / denominator;
        if(intersectScalar <= 0) return Double.MAX_VALUE;

        Vector3D intersect = origin.add(direction.scalarMultiply(intersectScalar));

        Vector3D prevLine = points.get(points.size()-1).subtract(intersect);
        double angleSum = 0;
        for(Vector3D point: points){
            Vector3D curLine =point.subtract(intersect);
            angleSum += Math.acos(prevLine.dot(curLine)/(prevLine.magnitude() * curLine.magnitude()));
            prevLine = curLine;
        }

        return angleSum > Math.PI*2 - ANGLE_ERROR_MARGIN && angleSum < Math.PI*2 + ANGLE_ERROR_MARGIN
                ? intersectScalar
                : Double.MAX_VALUE;

    }

    @Override
    public Color getColor() {
        return color;
    }
}
