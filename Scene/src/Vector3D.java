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

    public Vector3D scalarMultiply(double scalar){
        return new Vector3D(this.x*scalar, this.y * scalar, this.z * scalar);
    }

    public double dot(Vector3D other){
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    public Vector3D subtract(Vector3D other){
        return new Vector3D(this.x - other.x, this.y - other.y, this.z - other.z);
    }

    public Vector3D add(Vector3D other){
        return new Vector3D(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    public Vector3D cross(Vector3D other){
        return new Vector3D(
                this.y * other.z - this.z * other.y,
                this.z * other.x - this.x * other.z,
                this.x * other.y - this.y * other.x
        );
    }

    public double magnitude(){
        return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    public void normalizeThis(){
        double magnitude = this.magnitude();
        this.x /= magnitude;
        this.y /= magnitude;
        this.z /= magnitude;
    }

    public Vector3D normal(){
        double magnitude = this.magnitude();
        return new Vector3D(this.x/magnitude, this.y/magnitude, this.z/magnitude);
    }
}
