import java.awt.image.BufferedImage;

/**
 * A camera that will render worlds passed into it via ray tracing. Future update will do camera-coordinate transformation.
 * @author Adrian Postolache axp3806@rit.edu
 */
public class Camera {

    public static final int IMAGE_TYPE = BufferedImage.TYPE_INT_ARGB;

    // This is used to determine a 'right' vector from a lookAt vector
    private static final Vector3D GLOBAL_UP = new Vector3D(0,0,1);

    private Vector3D position;
    private Vector3D lookAt;
    private Vector3D up;
    private Vector3D right;
    private int imageLength;

    public Camera(Vector3D position, Vector3D lookAt, int imageLength){
        this.position = position;
        this.lookAt = lookAt.normal();
        this.right = GLOBAL_UP.cross(lookAt).normal();
        this.up = this.lookAt.cross(this.right).normal();
        this.imageLength = imageLength;
    }

    /**
     * Takes in a world and shoots a ray into it for every pixel to render the objects in the world.
     * @param world world to be rendered
     * @return the rendered image
     */
    public BufferedImage renderWorld(World world){

        BufferedImage output = new BufferedImage(imageLength, imageLength, IMAGE_TYPE);

        double stepDistance = 1.0/((double) imageLength);
        double halfStep = stepDistance/2;

        Vector3D sideStep = right.scalarMultiply(stepDistance);
        Vector3D halfSideStep = sideStep.scalarMultiply(.5);
        Vector3D upStep = up.scalarMultiply(stepDistance);
        Vector3D halfUpStep = upStep.scalarMultiply(.5);

        Vector3D centerScreen = position.add(lookAt);

        Vector3D leftStep = centerScreen.subtract(halfSideStep);
        Vector3D rightStep = centerScreen.add(halfSideStep);

        Color[][] pixelColors = new Color[imageLength][imageLength];

        double maxRadiance = 0;

        int leftX = imageLength/2 - 1;
        int rightX = imageLength/2;
        int upY = imageLength/2 - 1;
        int downY = imageLength/2;
        for(int x = 0; x < imageLength/2; x++){
            Vector3D leftUpStep = leftStep.add(halfUpStep);
            Vector3D leftDownStep = leftStep.subtract(halfUpStep);

            Vector3D rightUpStep = rightStep.add(halfUpStep);
            Vector3D rightDownStep = rightStep.subtract(halfUpStep);
            for(int y = 0; y < imageLength/2; y++){
                Color upLeftColor = world.traceRay(new Ray(position,leftUpStep));
                Color downLeftColor = world.traceRay(new Ray(position,leftDownStep));
                Color upRightColor = world.traceRay(new Ray(position,rightUpStep));
                Color downRightColor = world.traceRay(new Ray(position,rightDownStep));

                pixelColors[leftX - x][upY - y] = upLeftColor;
                pixelColors[leftX - x][downY + y] = downLeftColor;
                pixelColors[rightX + x][upY - y] = upRightColor;
                pixelColors[rightX + x][downY + y] = downRightColor;

                if(upLeftColor.getHighestColorValue() > maxRadiance){
                    maxRadiance = upLeftColor.getHighestColorValue();
                }

                if(downLeftColor.getHighestColorValue() > maxRadiance){
                    maxRadiance = downLeftColor.getHighestColorValue();
                }

                if(upRightColor.getHighestColorValue() > maxRadiance){
                    maxRadiance = upRightColor.getHighestColorValue();
                }

                if(downRightColor.getHighestColorValue() > maxRadiance){
                    maxRadiance = downRightColor.getHighestColorValue();
                }

                leftUpStep = leftUpStep.add(upStep);
                rightUpStep = rightUpStep.add(upStep);

                leftDownStep = leftDownStep.subtract(upStep);
                rightDownStep = rightDownStep.subtract(upStep);
            }
            leftStep = leftStep.subtract(sideStep);
            rightStep = rightStep.add(sideStep);
        }

        if(maxRadiance<1) maxRadiance = 1;

        for(int x = 0; x < imageLength; x++){
            for(int y = 0; y < imageLength; y++){
                output.setRGB(x, y, pixelColors[x][y].getRgbaWithMaxScale(maxRadiance));
            }
        }

        return output;
    }

}
