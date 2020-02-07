import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        World world = new World();
        Camera camera = new Camera(new Vector3D(0,0,0), new Vector3D(0,0,1), 720);
        BufferedImage image = camera.renderWorld(world);
        File output = new File("test.jpg");
        ImageIO.write(image,"jpg",output);
    }
}
