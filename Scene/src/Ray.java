/**
 * A ray for tracing purposes
 */
public class Ray {
    private Vector3D origin;
    private Vector3D direction;

    public Ray(Vector3D from, Vector3D to) {
        this.origin = from;
        this.direction = to.subtract(from).normal();
    }

    public Vector3D getOrigin() {
        return origin;
    }

    public Vector3D getDirection() {
        return direction;
    }
}
