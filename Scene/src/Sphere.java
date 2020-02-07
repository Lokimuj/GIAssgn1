public class Sphere extends Entity {

    public Sphere(){
        super(null);
    }

    @Override
    public double intersect(Ray ray) {
        return 0;
    }

    @Override
    public Color hit(Ray ray) {
        return null;
    }
}
