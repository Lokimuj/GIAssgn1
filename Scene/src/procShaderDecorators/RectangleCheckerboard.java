package procShaderDecorators;


import javafx.scene.shape.Shape3D;
import utils.Color;
import utils.Ray;
import utils.Vector3D;
import world.IntersectData;
import world.Light;
import world.World;
import world.entities.Rectangle;
import world.entities.ReflectiveProperties;

import java.util.Collections;
import java.util.List;

public class RectangleCheckerboard extends RectangleTextureDecorator {

    private double firstSideSize;
    private double secondSideSize;
    private Color evenTileColor;
    private Color oddTileColor;

    public RectangleCheckerboard(
            Rectangle originalRectangle,
            double firstSideSize,
            double secondSideSize,
            Color evenTileColor,
            Color oddTileColor
    ){
        super(originalRectangle);
        this.firstSideSize = firstSideSize;
        this.secondSideSize = secondSideSize;
        this.evenTileColor = evenTileColor;
        this.oddTileColor = oddTileColor;
    }



    public Color getColor(World world, IntersectData intersect, List<Light> visibleLights){

        // Figure out which checker color should be displayed
        Vector3D cornerToIntersection = intersect.point.subtract(originalRectangle.getCorner());
        double firstSideDistance = originalRectangle.getFirstSide().projectOntoThis(cornerToIntersection);
        double secondSideDistance = originalRectangle.getSecondSide().projectOntoThis(cornerToIntersection);
        Color currentColor;
        if(((int)(firstSideDistance/firstSideSize) + (int)(secondSideDistance / secondSideSize)) % 2 == 0){
            currentColor = evenTileColor;
        } else {
            currentColor = oddTileColor;
        }
        // Update the color on the rectangle for this pixel
        originalRectangle.getReflectiveProperties().setAmbDiffColor(currentColor);

        // Return rest of color calculations with this alteration
        return originalRectangle.getColor(world,intersect,visibleLights);
    }

}
