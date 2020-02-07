public abstract class Entity {

    private Vector3D positiion;

    public Entity(Vector3D position) {
        this.positiion = position;
    }

    public abstract double intersect(Ray ray);

    public abstract Color hit(Ray ray);
}
