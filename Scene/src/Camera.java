import java.awt.image.BufferedImage;

public class Camera {

    public static final int IMAGE_TYPE = BufferedImage.TYPE_INT_ARGB;
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

                output.setRGB(leftX-x, upY-y, upLeftColor.getRgba());
                output.setRGB(leftX-x, downY+y, downLeftColor.getRgba());
                output.setRGB(rightX+x, upY-y, upRightColor.getRgba());
                output.setRGB(rightX+x, downY+y, downRightColor.getRgba());

                leftUpStep = leftUpStep.add(upStep);
                rightUpStep = rightUpStep.add(upStep);

                leftDownStep = leftDownStep.subtract(upStep);
                rightDownStep = rightDownStep.subtract(upStep);
            }
            leftStep = leftStep.subtract(sideStep);
            rightStep = rightStep.add(sideStep);
        }

        return output;
    }

}
