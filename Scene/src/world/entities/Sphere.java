package world.entities;

import utils.Color;
import world.IntersectData;
import world.Light;
import world.World;
import world.entities.Entity;
import utils.Ray;
import utils.Vector3D;
import world.entities.ReflectiveProperties;

import java.util.List;

/**
 * world.entities.Sphere entity
 */
public class Sphere extends Entity {

    private Vector3D center;
    private double radius;

    public Sphere(){}

    public Sphere(
            Vector3D center,
            double radius,
            ReflectiveProperties reflectiveProperties,
            World world){
        super(reflectiveProperties, world);
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
        if(testResult > World.SAME_OBJECT_ERROR_MARGIN && testResult < result){
            result = testResult;
        }

        testResult = (-b - Math.sqrt(discriminant))/2;
        if(testResult > World.SAME_OBJECT_ERROR_MARGIN && testResult < result){
            result = testResult;
        }

        if(result == Double.MAX_VALUE){
            return new IntersectData(null, null, null, result);
        }

        Vector3D intersectionPoint = ray.extendToPoint(result);
        Vector3D normal = intersectionPoint.subtract(center).normal();

        return new IntersectData(intersectionPoint,normal,direction,result);
    }

    @Override
    public Color getColor(World world, IntersectData intersect, List<Light> visibleLights, int depth) {
        Color color = super.getColor(world, intersect, visibleLights, depth);

        Color transmissiveColor = new Color(0,0,0);
        if(depth < 100 && reflectiveProperties.getTransmissiveCoefficient() > 0){
            Vector3D transmissiveVector = Vector3D.refract(
                    intersect.lookAt,
                    intersect.normal,
                    World.AIR_REFRACTION_INDEX,
                    reflectiveProperties.getRefractionIndex());
            IntersectData currentIntersect = intersect;
            Ray transmissionRay = new Ray(intersect.point, transmissiveVector);
            int tempDepth = 0;
            while (tempDepth < 5 && transmissiveVector.dot(currentIntersect.normal) < 0){
                IntersectData newIntersect = this.intersect(transmissionRay);
                if(newIntersect.point.subtract(currentIntersect.point).magnitude()<.1){
                    System.out.println("oof");
                }
                currentIntersect = newIntersect;
                transmissiveVector = Vector3D.refract(
                        currentIntersect.lookAt,
                        currentIntersect.normal.scalarMultiply(-1),
                        reflectiveProperties.getRefractionIndex(),
                        World.AIR_REFRACTION_INDEX
                );
                transmissionRay = new Ray(currentIntersect.point, transmissiveVector);
                tempDepth++;
            }
            transmissiveColor = world.traceRay(transmissionRay,depth+1);
        }

        return new Color(
                color.add(transmissiveColor.scalarMultiply(reflectiveProperties.getTransmissiveCoefficient()))
        );
    }
}
