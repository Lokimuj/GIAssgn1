import java.util.ArrayList;
import java.util.List;

/**
 * Holds objects to be rendered and determines which objects are being hit by a ray.
 */
public class World {

    private static final Color BACKGROUND_COLOR = new Color(0xFFCCCCCC);

    private List<Entity> objects = new ArrayList<>();

    public Color traceRay(Ray ray){

        double shortestDistance = Double.MAX_VALUE;
        Entity closestObject = null;

        for(Entity object: objects){
            double currentDistance = object.intersect(ray);
            if(currentDistance > 0 && currentDistance<shortestDistance){
                shortestDistance = currentDistance;
                closestObject = object;
            }
        }

        return closestObject == null ? BACKGROUND_COLOR : closestObject.getColor();
    }

    public void addObject(Entity object){
        this.objects.add(object);
    }
}
