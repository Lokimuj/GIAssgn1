package utils;

import utils.Vector3D;

/**
 * A ray for tracing purposes
 */
public class Ray {
    private Vector3D origin;
    private Vector3D direction;
    private double magnitude;

    public Ray(Vector3D from, Vector3D to) {
        this.origin = from;
        this.direction = to.subtract(from);
        this.magnitude = direction.magnitude();
        this.direction = this.direction.normal();
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
