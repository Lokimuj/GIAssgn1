public abstract class Entity {

    protected double x;
    protected double y;
    protected double z;

    public Entity(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public abstract double intersect(Ray ray);

    public abstract Color hit(Ray ray);
}
