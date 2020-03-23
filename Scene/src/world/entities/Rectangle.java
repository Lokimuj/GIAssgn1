package world.entities;

import utils.Vector3D;

public class Rectangle extends Polygon {

    private Vector3D corner;
    private Vector3D firstSide;
    private Vector3D secondSide;

    public Rectangle(){}

    public Rectangle(
            ReflectiveProperties reflectiveProperties,
            Vector3D corner,
            Vector3D firstSide,
            Vector3D secondSide
    ){
        super(
                reflectiveProperties,
                corner.add(firstSide),
                corner,
                corner.add(secondSide),
                corner.add(firstSide).add(secondSide)
        );
        this.corner = corner;
        this.firstSide = firstSide;
        this.secondSide = secondSide;
    }


    public Vector3D getCorner() {
        return corner;
    }

    public Vector3D getFirstSide() {
        return firstSide;
    }

    public Vector3D getSecondSide() {
        return secondSide;
    }
}
