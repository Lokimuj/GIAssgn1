package utils;

/**
 * 3D vector class that has useful vector utilities
 * @Author Adrian Postolache axp3806@rit.edu
 */
public class Vector3D {

    private double x;
    private double y;
    private double z;

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }



    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    /**
     * @param scalar
     * @return this vector scaled by the scalar
     */
    public Vector3D scalarMultiply(double scalar){
        return new Vector3D(this.x * scalar, this.y * scalar, this.z * scalar);
    }

    /**
     * @param other
     * @return dot product of this and the other vector
     */
    public double dot(Vector3D other){
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    /**
     * @param other
     * @return this vector minus other vector
     */
    public Vector3D subtract(Vector3D other){
        return new Vector3D(this.x - other.x, this.y - other.y, this.z - other.z);
    }

    /**
     * @param other
     * @return this vector plus other vector
     */
    public Vector3D add(Vector3D other){
        return new Vector3D(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    /**
     * @param other
     * @return cross product of this and other vector
     */
    public Vector3D cross(Vector3D other){
        return new Vector3D(
                this.y * other.z - this.z * other.y,
                this.z * other.x - this.x * other.z,
                this.x * other.y - this.y * other.x
        );
    }

    /**
     * @return magnitude of this vector
     */
    public double magnitude(){
        return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    /**
     * @return this vector normalized
     */
    public Vector3D normal(){
        double magnitude = this.magnitude();
        return new Vector3D(this.x/magnitude, this.y/magnitude, this.z/magnitude);
    }

    public double projectOntoThis(Vector3D other){
        return other.dot(this)/this.magnitude();
    }

    /**
     * Assumes both input vectors are normalized, returns vector of incidence
     * @param incoming
     * @param normal
     * @return
     */
    public static Vector3D incidence(Vector3D incoming, Vector3D normal){
        double coefficient = 2 * incoming.dot(normal);
        return incoming.subtract(normal.scalarMultiply(coefficient));
    }

    @Override
    public String toString() {
        return "[ " + this.x + ", "+ this.y + ", " + this.z + "]";
    }
}
