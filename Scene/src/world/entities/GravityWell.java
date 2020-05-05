package world.entities;

import utils.Color;
import utils.Ray;
import utils.TransformMatrix;
import utils.Vector3D;
import world.IntersectData;
import world.Light;
import world.World;

import java.util.List;

public class GravityWell extends Sphere {

    private double horizonRadius;
    private double maximumDot;
    private double whip;

    public GravityWell(Vector3D center, double sphereOfInfluence, double horizonRadius, double whip, World world){
        super(center,sphereOfInfluence,null,world);
        this.horizonRadius = horizonRadius;
        double coneHeight = sphereOfInfluence + horizonRadius;
        double tangentToHorizon = horizonRadius/Math.sqrt(coneHeight * coneHeight - 2 * coneHeight * horizonRadius);
        this.maximumDot = Math.cos(Math.atan(tangentToHorizon));
        this.whip = whip;
    }

    @Override
    public Color getColor(World world, IntersectData intersect, List<Light> visibleLights, int depth) {
        double dot = intersect.lookAt.scalarMultiply(-1).dot(intersect.normal);
        if(dot >= maximumDot){
            return new Color(0,0,0);
        }
        double angleOfRotation =  whip * Math.PI * Math.pow(dot,1.5);
        Vector3D axisOfRotation = intersect.normal.cross(intersect.lookAt).normal();

        TransformMatrix rotation = TransformMatrix.rotateAroundAxis(axisOfRotation,angleOfRotation);
        TransformMatrix translate = TransformMatrix.translateTransformAndBack(this.center.scalarMultiply(-1),rotation);

        Vector3D newDirection = rotation.leftMultiply(Vector3D.incidence(intersect.lookAt,intersect.normal));
        Vector3D newPosition = translate.leftMultiply(intersect.point);

        Ray newRay = new Ray(newPosition,newDirection);


        return world.traceRay(newRay, depth + 1);
    }
}
