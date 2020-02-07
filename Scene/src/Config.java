import java.io.*;
import java.util.List;

public class Config {

    public static final String ATTRIBUTE_DELIMITER = ";";
    public static final String LIST_DELIMITER = ",";

    private Camera camera;
    private List<Entity> objects;

    public void readConfigFile(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while((line = br.readLine()) != null){
            String[] parts = line.split(ATTRIBUTE_DELIMITER);
            switch(parts[0]){
                case "camera":
                    String[] positionArguments = parts[1].split(LIST_DELIMITER);
                    Vector3D cameraPosition = new Vector3D(
                            Double.parseDouble(positionArguments[0]),
                            Double.parseDouble(positionArguments[1]),
                            Double.parseDouble(positionArguments[2])
                    );

                    String[] lookAtArguments = parts[2].split(LIST_DELIMITER);
                    Vector3D cameraLookAt = new Vector3D(
                            Double.parseDouble(lookAtArguments[0]),
                            Double.parseDouble(lookAtArguments[1]),
                            Double.parseDouble(lookAtArguments[2])
                    );

                    int imageSize = Integer.parseInt(parts[3]);
                    camera = new Camera(cameraPosition,cameraLookAt,imageSize);
                    break;
            }
        }
    }
}
