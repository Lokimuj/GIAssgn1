package utils;

/**
 * Represents a color to be sent along rays. Will be more complicated with further assignments.
 * xyz equate to rgb radiance
 */
public class Color extends Vector3D {

    public Color(int rgba){
        this(
            (double) ((rgba >> 16) & 0xFF) / 0xFF,
            (double) ((rgba >> 8) & 0xFF) / 0xFF,
            (double) ( rgba & 0xFF) / 0xFF
        );
    }

    public Color(double red, double green, double blue) {
        super(red,green,blue);
    }

    public Color( Vector3D colorVector ){
        this(colorVector.getX(), colorVector.getY(), colorVector.getZ());
    }

    public double getHighestColorValue(){
        double max = this.getX() > this.getY() ? this.getX() : this.getY();
        return max > this.getZ() ? max : this.getZ();
    }

    public int getRgbaWithMaxScale(double biggestRadiance) {
        Vector3D scaled = this.scalarMultiply(1.0 / biggestRadiance).scalarMultiply((double) 0xFF);
        int red = (int) scaled.getX();
        int green = (int) scaled.getY();
        int blue = (int) scaled.getZ();
        int rgba = (0xFF << 24) | (red << 16) | (green << 8) | blue;
        return rgba;
    }

    public Color multiplyColor(Color otherColor){
        return new Color(
                this.getX() * otherColor.getX(),
                this.getY() * otherColor.getY(),
                this.getZ() * otherColor.getZ()
        );
    }
}
