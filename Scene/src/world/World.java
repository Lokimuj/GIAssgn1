package world;

import world.entities.Entity;
import utils.Color;
import utils.Ray;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds objects to be rendered and determines which objects are being hit by a ray.
 */
public class World {

    public static final double SAME_OBJECT_ERROR_MARGIN = 1e-10;

    public static final Color BACKGROUND_COLOR = new Color(1,1,1);

    private Color ambientColor ;

    private List<Entity> objects = new ArrayList<>();

    private List<Light> lights = new ArrayList<>();

    public Color traceRay(Ray ray){

        double shortestDistance = Double.MAX_VALUE;
        IntersectData shortestIntersect = null;
        Entity closestObject = null;

        for(Entity object: objects){
            IntersectData currentIntersect = object.intersect(ray);
            if(currentIntersect.distance > SAME_OBJECT_ERROR_MARGIN && currentIntersect.distance < shortestDistance){
                shortestDistance = currentIntersect.distance;
                shortestIntersect = currentIntersect;
                closestObject = object;
            }
        }

        if(closestObject == null){
            return BACKGROUND_COLOR;
        }
        List<Light> visibleLights = shadowCheck(shortestIntersect);
        return closestObject.getColor(this, shortestIntersect, visibleLights);
    }

    private List<Light> shadowCheck(IntersectData intersectData){
        List<Light> visibleLights = new ArrayList<>();
        for(Light light : lights){
            Ray toLight = new Ray(intersectData.point, light.getPosition());

            //Ignore the light if it comes from behind the surface normal
            if(toLight.getDirection().dot(intersectData.normal) < 0){
                continue;
            }

            double distanceToLight = toLight.getOriginalMagnitude();
            boolean isBlocked = false;

            for(Entity object: objects){
                double currentDistance = object.intersect(toLight).distance;
                if(currentDistance > SAME_OBJECT_ERROR_MARGIN && currentDistance < distanceToLight){
                    isBlocked = true;
                    break;
                }
            }
            if(!isBlocked){
                visibleLights.add(light);
            }
        }

        return visibleLights;
    }

    public void addObject(Entity object){
        this.objects.add(object);
    }

    public void addLight(Light light){
        this.lights.add(light);
    }

    public Color getAmbientColor(){
        return ambientColor;
    }

    public void setAmbientColor(Color ambientColor) {
        this.ambientColor = ambientColor;
    }
}
