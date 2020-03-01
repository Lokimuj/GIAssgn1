import java.io.*;

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
                    double radius = Double.parseDouble(parts[1]);
                    Vector3D spherePosition = Vector3D.parseVector(parts[2]);
                    ReflectiveProperties sphereReflective = ReflectiveProperties.readFromString(parts[3]);

                    world.addObject(new Sphere(spherePosition, radius, sphereReflective));
                    break;
                case "rectangle":
                    Vector3D rectCenter = Vector3D.parseVector(parts[1]);
                    Vector3D rectSide1 = Vector3D.parseVector(parts[2]);
                    Vector3D rectSide2 = Vector3D.parseVector(parts[3]);
                    ReflectiveProperties rectReflective = ReflectiveProperties.readFromString(parts[4]);

                    world.addObject(Polygon.createRectangle(rectCenter, rectSide1, rectSide2, rectReflective));
                    break;
                case "light":
                    Vector3D lightPosition = Vector3D.parseVector(parts[1]);
                    Color lightColor = new Color(Vector3D.parseVector(parts[2]));
                    world.addLight(new Light(lightPosition,lightColor));
                    break;
                case "ambient":
                    Color ambientColor = new Color(Vector3D.parseVector(parts[1]));
                    world.setAmbientColor(ambientColor);
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
