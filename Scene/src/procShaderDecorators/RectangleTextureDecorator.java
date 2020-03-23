package procShaderDecorators;

import utils.Ray;
import utils.Vector3D;
import world.IntersectData;
import world.entities.Rectangle;
import world.entities.ReflectiveProperties;

public class RectangleTextureDecorator extends Rectangle {

    protected Rectangle originalRectangle;

    public RectangleTextureDecorator(Rectangle originalRectangle){
        this.originalRectangle = originalRectangle;
    }

    public IntersectData intersect(Ray ray){
        return originalRectangle.intersect(ray);
    }

    @Override
    public ReflectiveProperties getReflectiveProperties() {
        return originalRectangle.getReflectiveProperties();
    }

    public Vector3D getCorner() {
        return originalRectangle.getCorner();
    }

    public Vector3D getFirstSide() {
        return originalRectangle.getFirstSide();
    }

    public Vector3D getSecondSide() {
        return originalRectangle.getSecondSide();
    }
}
