public class IntersectData {
    public final Vector3D point;
    public final Vector3D normal;
    public final Vector3D lookAt;
    public final double distance;

    public IntersectData(Vector3D point, Vector3D normal, Vector3D lookAt, double distance) {
        this.point = point;
        this.normal = normal;
        this.lookAt = lookAt;
        this.distance = distance;
    }
}
