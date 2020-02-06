import java.util.ArrayList;
import java.util.List;

public class World {

    private static final Color BACKGROUND_COLOR = new Color(0xFFFFFFFF);

    private List<Entity> objects = new ArrayList<>();

    public Color traceRay(Ray ray){

        double shortestDistance = Double.MAX_VALUE;
        Entity closestObject = null;

        for(Entity object: objects){
            double currentDistance = object.intersect(ray);
            if(currentDistance<shortestDistance){
                shortestDistance = currentDistance;
                closestObject = object;
            }
        }

        return closestObject == null ? BACKGROUND_COLOR : closestObject.hit(ray);
    }
}
