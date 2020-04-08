package utils;

import utils.Vector3D;

/**
 * A ray for tracing purposes
 */
public class Ray {
    private Vector3D origin;
    private Vector3D direction;
    private double magnitude;

    public Ray(Vector3D start, Vector3D direction) {
        this.origin = start;
        this.magnitude = direction.magnitude();
        this.direction = direction.normal();
    }

    public static Ray constructFromTwoPoints(Vector3D start, Vector3D end){
        return new Ray(start, end.subtract(start));
    }

    public Vector3D getOrigin() {
        return origin;
    }

    public Vector3D getDirection() {
        return direction;
    }

    public double getOriginalMagnitude(){
        return magnitude;
    }

    public Vector3D extendToPoint(double scalar){
        return origin.add(direction.scalarMultiply(scalar));
    }
}
