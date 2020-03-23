package world;

import utils.Color;
import utils.Vector3D;

public class Light {

    private Vector3D position;
    private Color color;

    public Light(Vector3D position, Color color) {
        this.position = position;
        this.color = color;
    }

    public Vector3D getPosition() {
        return position;
    }

    public Color getColor() {
        return color;
    }

}
