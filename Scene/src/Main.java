import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        if(args.length != 1){
            System.out.println("Usage: java Main configFile");
            System.exit(1);
        }
        File configFile = new File(args[0]);
        Config config = new Config();
        config.readConfigFile(configFile);
        World world = config.getWorld();
        Camera camera = config.getCamera();

        BufferedImage image = camera.renderWorld(world);
        File output = new File("output.png");
        ImageIO.write(image,"png",output);
    }

    void oldTestFunction() throws IOException {
        World world = new World();
        Sphere sphere = new Sphere(new Vector3D(1,10,0),2.0,new Color(0xFFFFFF00));
        Polygon polygon = new Polygon(
                new Color(0xFF00FFFF),
                new Vector3D(1,3,-1),
                new Vector3D(0,5,1),
                new Vector3D(-1,3,-1)
        );
        world.addObject(sphere);
        world.addObject(polygon);
        Camera camera = new Camera(new Vector3D(0,0,0), new Vector3D(0,1,0), 1440);
        BufferedImage image = camera.renderWorld(world);
        File output = new File("test.png");
        ImageIO.write(image,"png",output);
    }
}
