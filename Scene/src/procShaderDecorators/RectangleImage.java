package procShaderDecorators;

import org.w3c.dom.css.Rect;
import utils.Color;
import utils.Vector3D;
import world.IntersectData;
import world.Light;
import world.World;
import world.entities.Rectangle;

import java.awt.image.BufferedImage;
import java.util.List;

public class RectangleImage extends RectangleTextureDecorator {

    private BufferedImage image;
    double xMagnitude;
    double yMagnitude;
    int imageWidth;
    int imageHeight;

    public RectangleImage(Rectangle originalRectangle, BufferedImage image){
        super(originalRectangle);
        xMagnitude = getFirstSide().magnitude();
        yMagnitude = getSecondSide().magnitude();
        this.image = image;
        imageWidth = image.getWidth();
        imageHeight = image.getHeight();
    }

    public Color getColor(World world, IntersectData intersect, List<Light> visibleLights) {

        // Figure out which checker color should be displayed
        Vector3D cornerToIntersection = intersect.point.subtract(originalRectangle.getCorner());
        double firstSideDistance = originalRectangle.getFirstSide().projectOntoThis(cornerToIntersection);
        double secondSideDistance = originalRectangle.getSecondSide().projectOntoThis(cornerToIntersection);

        int xIndex = Math.max(Math.min((int) (firstSideDistance / xMagnitude * imageWidth), imageWidth-1), 0);
        int yIndex = Math.max(Math.min((int) (secondSideDistance / yMagnitude * imageHeight), imageHeight-1), 0);

//        System.out.println(String.format("0x%08X", (image.getRGB(xIndex,yIndex) >> 16) & 0xFF));

        Color pixelColor = new Color(image.getRGB(xIndex,yIndex));

        getReflectiveProperties().setAmbDiffColor(pixelColor);
        return originalRectangle.getColor(world,intersect,visibleLights);
    }

}
