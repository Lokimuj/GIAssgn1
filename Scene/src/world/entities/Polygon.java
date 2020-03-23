package world.entities;

import utils.Ray;
import utils.Vector3D;
import world.IntersectData;

import java.util.Arrays;
import java.util.List;

/**
 * world.entities.Entity that is a co-planar polygon to be hit by rays
 */
public class Polygon extends Entity {

    private static final double ANGLE_ERROR_MARGIN = (2 * Math.PI)/1000.0;

    protected List<Vector3D> points;
    protected Vector3D planeNormal;
    protected double planeConstant;

    public Polygon(){}

    public Polygon(ReflectiveProperties reflectiveProperties, Vector3D... points){
        super(reflectiveProperties);
        if(points.length < 3){
            throw new IllegalArgumentException("world.entities.Polygon requires at least 3 points");
        }
        this.points = Arrays.asList(points);
        Vector3D firstSide = points[0].subtract(points[1]);
        Vector3D secondSide = points[2].subtract(points[1]);
        planeNormal = secondSide.cross(firstSide);
        planeConstant = -1 * planeNormal.dot(points[0]);
    }


    public static Polygon createRectangle(Vector3D center,
                                          Vector3D sideVector1,
                                          Vector3D sideVector2,
                                          ReflectiveProperties reflectiveProperties){
        return new Polygon(
                reflectiveProperties,
                center.add(sideVector1).subtract(sideVector2),
                center.add(sideVector1).add(sideVector2),
                center.subtract(sideVector1).add(sideVector2),
                center.subtract(sideVector1).subtract(sideVector2)
        );
    }

    @Override
    public IntersectData intersect(Ray ray) {
        Vector3D origin = ray.getOrigin();
        Vector3D direction = ray.getDirection();

        double denominator = direction.dot(planeNormal);
        if(denominator == 0) return new IntersectData(null,null,null, Double.MAX_VALUE);
        double intersectScalar = - (origin.dot(planeNormal) + planeConstant) / denominator;
        if(intersectScalar <= 0) return new IntersectData(null,null,null, Double.MAX_VALUE);

        Vector3D intersect = origin.add(direction.scalarMultiply(intersectScalar));

        Vector3D prevLine = points.get(points.size()-1).subtract(intersect);
        double angleSum = 0;
        for(Vector3D point: points){
            Vector3D curLine =point.subtract(intersect);
            angleSum += Math.acos(prevLine.dot(curLine)/(prevLine.magnitude() * curLine.magnitude()));
            prevLine = curLine;
        }

        if(angleSum > Math.PI*2 - ANGLE_ERROR_MARGIN && angleSum < Math.PI*2 + ANGLE_ERROR_MARGIN){
            Vector3D intersectPoint = ray.extendToPoint(intersectScalar);
            return new IntersectData(intersectPoint, planeNormal.normal(), direction, intersectScalar);
        }

        return new IntersectData(null, null, null, Double.MAX_VALUE);
    }

}
