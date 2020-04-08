package world.entities;

import utils.Color;
import utils.Ray;
import world.IntersectData;
import world.Light;
import world.World;

import java.util.List;

public interface Shape3D {
    /**
     * @param ray
     * @return closest distance along the ray that intersects with this entity
     */
    IntersectData intersect(Ray ray);

    /**
     * color to be sent back along the ray if one hits this entity. Will be updated with more complicated functionality
     * @return
     */
    Color getColor(World world, IntersectData intersect, List<Light> visibleLights, int depth);
}
