import java.io.*;
import java.util.List;

/**
 * Reads in a config file and builds a world and camera from it to be used for rendering
 */
public class Config {

    public static final String ATTRIBUTE_DELIMITER = ";";
    public static final String LIST_DELIMITER = ",";

    private Camera camera;
    private World world;

    public void readConfigFile(File file) throws IOException {

        this.world = new World();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while((line = br.readLine()) != null){
            String[] parts = line.split(ATTRIBUTE_DELIMITER);
            switch(parts[0]){
                case "camera":
                    Vector3D cameraPosition = Vector3D.parseVector(parts[1]);

                    Vector3D cameraLookAt = Vector3D.parseVector(parts[2]);

                    int imageSize = Integer.parseInt(parts[3]);
                    camera = new Camera(cameraPosition,cameraLookAt,imageSize);
                    break;
                case "sphere":
                    int sphereColor = 0xFF000000 | Integer.parseInt(parts[1],16);
                    double radius = Double.parseDouble(parts[2]);
                    Vector3D spherePosition = Vector3D.parseVector(parts[3]);

                    world.addObject(new Sphere(spherePosition, radius, new Color(sphereColor)));
                    break;
                case "rectangle":
                    int rectColor = 0xFF000000 | Integer.parseInt(parts[1],16);
                    Vector3D rectCenter = Vector3D.parseVector(parts[2]);
                    Vector3D rectSide1 = Vector3D.parseVector(parts[3]);
                    Vector3D rectSide2 = Vector3D.parseVector(parts[4]);

                    world.addObject(Polygon.createRectangle(new Color(rectColor), rectCenter, rectSide1, rectSide2));
                    break;
            }
        }
    }

    public Camera getCamera() {
        return camera;
    }

    public World getWorld() {
        return world;
    }
}
