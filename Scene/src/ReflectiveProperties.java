public class ReflectiveProperties {

    public static final String DELIMITER = "\\|";

    private Color ambDiffColor;
    private Color specColor;

    private double ambientCoefficient;
    private double diffuseCoefficient;
    private double specularCoefficient;
    private double specularExponent;

    public ReflectiveProperties(Color ambDiffColor, Color specColor, double ambientCoefficient, double diffuseCoefficient, double specularCoefficient, double specularExponent) {
        this.ambDiffColor = ambDiffColor;
        this.specColor = specColor;
        this.ambientCoefficient = ambientCoefficient;
        this.diffuseCoefficient = diffuseCoefficient;
        this.specularCoefficient = specularCoefficient;
        this.specularExponent = specularExponent;
    }

    public static ReflectiveProperties readFromString(String input){
        String[] parts = input.split(DELIMITER);
        Color ambDiffColor = new Color(Vector3D.parseVector(parts[0]));
        Color specColor = new Color(Vector3D.parseVector(parts[1]));
        double ambientCoefficient = Double.parseDouble(parts[2]);
        double diffuseCoefficient = Double.parseDouble(parts[3]);
        double specularCoefficient = Double.parseDouble(parts[4]);
        double specularExponent = Double.parseDouble(parts[5]);
        return new ReflectiveProperties(ambDiffColor,
                specColor,
                ambientCoefficient,
                diffuseCoefficient,
                specularCoefficient,
                specularExponent
        );
    }

    public Color getAmbDiffColor() {
        return ambDiffColor;
    }

    public Color getSpecColor() {
        return specColor;
    }

    public double getAmbientCoefficient() {
        return ambientCoefficient;
    }

    public double getDiffuseCoefficient() {
        return diffuseCoefficient;
    }

    public double getSpecularCoefficient() {
        return specularCoefficient;
    }

    public double getSpecularExponent() {
        return specularExponent;
    }
}
