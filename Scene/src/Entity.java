/**
 * An entity that can be rendered and hit by rays in teh world
 */
public interface Entity {

    /**
     * @param ray
     * @return closest distance along the ray that intersects with this entity
     */
    double intersect(Ray ray);

    /**
     * color to be sent back along the ray if one hits this entity. Will be updated with more complicated functionality
     * @return
     */
    Color getColor();
}
