import world.Camera;
import world.World;
import world.configReader.Config;

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
}
