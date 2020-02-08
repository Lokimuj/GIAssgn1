public class Sphere implements Entity {

    private Vector3D center;
    private double radius;
    private Color color;

    public Sphere(Vector3D center, double radius, Color color){
        this.center = center;
        this.radius = radius;
        this.color = color;
    }

    @Override
    public double intersect(Ray ray) {
        Vector3D direction = ray.getDirection();
        Vector3D origin = ray.getOrigin();
        Vector3D cameraToCenter = origin.subtract(center);

        double b = 2 * direction.dot(cameraToCenter);
        double c = cameraToCenter.dot(cameraToCenter) - radius * radius;

        double discriminant = b * b - 4 * c;

        if(discriminant < 0){
            return Double.MAX_VALUE;
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

        return result;
    }

    @Override
    public Color getColor() {
        return color;
    }
}
