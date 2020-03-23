package procShaderDecorators;

import utils.Color;
import utils.Vector3D;
import world.IntersectData;
import world.Light;
import world.World;
import world.entities.Rectangle;

import java.util.List;

public class RectangleWavy extends RectangleTextureDecorator {

    private double waveThickness;
    private double waveLength;
    private double amplitude;
    private Color oddColor;
    private Color evenColor;

    public RectangleWavy(
            Rectangle originalRectangle,
            double waveThickness,
            double waveLength,
            double amplitude,
            Color oddColor,
            Color evenColor
    ){
        super(originalRectangle);
        this.waveThickness = waveThickness;
        this.waveLength = waveLength;
        this.amplitude = amplitude;
        this.oddColor = oddColor;
        this.evenColor = evenColor;
    }

    public Color getColor(World world, IntersectData intersect, List<Light> visibleLights) {

        // Figure out which checker color should be displayed
        Vector3D cornerToIntersection = intersect.point.subtract(originalRectangle.getCorner());
        double firstSideDistance = originalRectangle.getFirstSide().projectOntoThis(cornerToIntersection);
        double secondSideDistance = originalRectangle.getSecondSide().projectOntoThis(cornerToIntersection);
        double adjustedHeight = firstSideDistance - amplitude * Math.sin(secondSideDistance * 2 *Math.PI / waveLength);
        double colorNumber = adjustedHeight / waveThickness;
        int evenCheck = Math.abs((int) colorNumber);
        if(colorNumber < 0){
            evenCheck++;
        }

        getReflectiveProperties().setAmbDiffColor(evenCheck % 2 == 0 ? evenColor : oddColor);

        return originalRectangle.getColor(world,intersect,visibleLights);
    }
}
