package procShaderDecorators;

import utils.Color;
import utils.Vector3D;
import world.IntersectData;
import world.Light;
import world.World;
import world.entities.Rectangle;

import java.util.List;

public class RectangleGradient extends RectangleTextureDecorator {

    private double firstSideSize;
    private double secondSideSize;

    public RectangleGradient(
            Rectangle originalRectangle
    ){
        super(originalRectangle);
        this.firstSideSize = originalRectangle.getFirstSide().magnitude();
        this.secondSideSize = originalRectangle.getSecondSide().magnitude();
    }



    public Color getColor(World world, IntersectData intersect, List<Light> visibleLights, int depth){

        // Figure out which checker color should be displayed
        Vector3D cornerToIntersection = intersect.point.subtract(originalRectangle.getCorner());
        double firstSideDistance = originalRectangle.getFirstSide().projectOntoThis(cornerToIntersection);
        double secondSideDistance = originalRectangle.getSecondSide().projectOntoThis(cornerToIntersection);
        Color currentColor = new Color(firstSideDistance/firstSideSize, secondSideDistance/secondSideSize,.5);
        // Update the color on the rectangle for this pixel
        originalRectangle.getReflectiveProperties().setAmbDiffColor(currentColor);

        // Return rest of color calculations with this alteration
        return originalRectangle.getColor(world,intersect,visibleLights, depth);
    }

}
