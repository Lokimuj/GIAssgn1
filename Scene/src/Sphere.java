/**
 * Sphere entity
 */
public class Sphere extends Entity {

    private Vector3D center;
    private double radius;

    public Sphere(
            Vector3D center,
            double radius,
            ReflectiveProperties reflectiveProperties){
        super(reflectiveProperties);
        this.center = center;
        this.radius = radius;
    }

    @Override
    public IntersectData intersect(Ray ray) {
        Vector3D direction = ray.getDirection();
        Vector3D origin = ray.getOrigin();
        Vector3D cameraToCenter = origin.subtract(center);

        double b = 2 * direction.dot(cameraToCenter);
        double c = cameraToCenter.dot(cameraToCenter) - radius * radius;

        double discriminant = b * b - 4 * c;

        if(discriminant < 0){
            return new IntersectData(null, null, null, Double.MAX_VALUE);
        }

        double result = Double.MAX_VALUE;

        double testResult = (-b + Math.sqrt(discriminant))/2;
        if(testResult > 0 && testResult < result){
            result = testResult;
        }

        testResult = (-b - Math.sqrt(discriminant))/2;
        if(testResult > 0 && testResult < result){
            result = testResult;
        }

        if(result == Double.MAX_VALUE){
            return new IntersectData(null, null, null, result);
        }

        Vector3D intersectionPoint = ray.extendToPoint(result);
        Vector3D normal = intersectionPoint.subtract(center).normal();

        return new IntersectData(intersectionPoint,normal,direction,result);
    }

}
