package world.configReader;

import procShaderDecorators.RectangleCheckerboard;
import procShaderDecorators.RectangleGradient;
import procShaderDecorators.RectangleImage;
import procShaderDecorators.RectangleWavy;
import world.Camera;
import world.Light;
import world.entities.Polygon;
import utils.Color;
import utils.Vector3D;
import world.World;
import world.entities.Rectangle;
import world.entities.ReflectiveProperties;
import world.entities.Sphere;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Reads in a config file and builds a world and camera from it to be used for rendering
 */
public class Config {

    public static final String ATTRIBUTE_DELIMITER = ";";
    public static final String LIST_DELIMITER = ",";
    public static final String SHADER_LINE_POSTFIX = "shader";

    /**
     * Takes in a string of the format "x,y,z" and creates a vector out of those numbers
     * @param vectorString
     * @return
     */
    public static Vector3D parseVector(String vectorString){
        String[] vectorArguments = vectorString.split(Config.LIST_DELIMITER);
        return new Vector3D(
                Double.parseDouble(vectorArguments[0]),
                Double.parseDouble(vectorArguments[1]),
                Double.parseDouble(vectorArguments[2])
        );
    }

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
                    Vector3D cameraPosition = parseVector(parts[1]);

                    Vector3D cameraLookAt = parseVector(parts[2]);

                    int imageSize = Integer.parseInt(parts[3]);
                    camera = new Camera(cameraPosition,cameraLookAt,imageSize);
                    break;
                case "sphere":
                    double radius = Double.parseDouble(parts[1]);
                    Vector3D spherePosition = parseVector(parts[2]);
                    ReflectiveProperties sphereReflective = ReflectiveProperties.readFromString(parts[3]);

                    world.addObject(new Sphere(spherePosition, radius, sphereReflective, world));
                    break;
                case "rectangle":
                    Vector3D rectCenter = parseVector(parts[1]);
                    Vector3D rectSide1 = parseVector(parts[2]);
                    Vector3D rectSide2 = parseVector(parts[3]);
                    ReflectiveProperties rectReflective = ReflectiveProperties.readFromString(parts[4]);

                    Rectangle finalRectangle = new Rectangle(rectReflective, rectCenter, rectSide1, rectSide2, world);
                    while(parts[parts.length-1].equals(SHADER_LINE_POSTFIX)){
                        line = br.readLine();
                        parts = line.split(ATTRIBUTE_DELIMITER);
                        switch (parts[0]) {
                            case "checker":
                                finalRectangle = new RectangleCheckerboard(
                                        finalRectangle,
                                        Double.parseDouble(parts[1]),
                                        Double.parseDouble(parts[2]),
                                        new Color(parseVector(parts[3])),
                                        new Color(parseVector(parts[4]))
                                );
                                break;
                            case "wavy":
                                finalRectangle = new RectangleWavy(
                                        finalRectangle,
                                        Double.parseDouble(parts[1]),
                                        Double.parseDouble(parts[2]),
                                        Double.parseDouble(parts[3]),
                                        new Color(parseVector(parts[4])),
                                        new Color(parseVector(parts[5]))
                                );
                                break;
                            case "image":
                                BufferedImage image = ImageIO.read(new File(parts[1]));
                                finalRectangle = new RectangleImage(finalRectangle,image);
                                break;
                            case "gradient":
                                finalRectangle = new RectangleGradient(finalRectangle);
                                break;
                        }
                    }

                    world.addObject(finalRectangle);
                    break;
                case "light":
                    Vector3D lightPosition = parseVector(parts[1]);
                    Color lightColor = new Color(parseVector(parts[2]));
                    world.addLight(new Light(lightPosition,lightColor));
                    break;
                case "ambient":
                    Color ambientColor = new Color(parseVector(parts[1]));
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
