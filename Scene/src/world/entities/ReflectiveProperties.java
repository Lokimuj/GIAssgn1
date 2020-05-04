package world.entities;

import utils.Color;
import utils.Vector3D;

import static world.configReader.Config.parseVector;

public class ReflectiveProperties {

    public static final String DELIMITER = "\\|";

    private Color ambDiffColor;
    private Color specColor;

    private double ambientCoefficient;
    private double diffuseCoefficient;
    private double specularCoefficient;
    private double specularExponent;
    private double reflectiveCoefficient;
    private double transmissiveCoefficient;
    private double refractionIndex;

    public ReflectiveProperties(Color ambDiffColor,
                                Color specColor,
                                double ambientCoefficient,
                                double diffuseCoefficient,
                                double specularCoefficient,
                                double specularExponent,
                                double reflectiveCoefficient,
                                double transmissiveCoefficient,
                                double refractionIndex) {
        this.ambDiffColor = ambDiffColor;
        this.specColor = specColor;
        this.ambientCoefficient = ambientCoefficient;
        this.diffuseCoefficient = diffuseCoefficient;
        this.specularCoefficient = specularCoefficient;
        this.specularExponent = specularExponent;
        this.reflectiveCoefficient = reflectiveCoefficient;
        this.transmissiveCoefficient = transmissiveCoefficient;
        this.refractionIndex = refractionIndex;
    }

    public static ReflectiveProperties readFromString(String input){
        String[] parts = input.split(DELIMITER);
        Color ambDiffColor = new Color(parseVector(parts[0]));
        Color specColor = new Color(parseVector(parts[1]));
        double ambientCoefficient = Double.parseDouble(parts[2]);
        double diffuseCoefficient = Double.parseDouble(parts[3]);
        double specularCoefficient = Double.parseDouble(parts[4]);
        double specularExponent = Double.parseDouble(parts[5]);
        double reflectiveCoefficient = Double.parseDouble(parts[6]);
        double transmissiveCoefficient = Double.parseDouble(parts[7]);
        double refractionIndex = Double.parseDouble(parts[8]);
        return new ReflectiveProperties(ambDiffColor,
                specColor,
                ambientCoefficient,
                diffuseCoefficient,
                specularCoefficient,
                specularExponent,
                reflectiveCoefficient,
                transmissiveCoefficient,
                refractionIndex
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

    public double getReflectiveCoefficient() {
        return reflectiveCoefficient;
    }

    public double getTransmissiveCoefficient() {
        return transmissiveCoefficient;
    }

    public double getRefractionIndex() {
        return refractionIndex;
    }

    public void setAmbDiffColor(Color ambDiffColor) {
        this.ambDiffColor = ambDiffColor;
    }

    public void setSpecColor(Color specColor) {
        this.specColor = specColor;
    }

    public void setAmbientCoefficient(double ambientCoefficient) {
        this.ambientCoefficient = ambientCoefficient;
    }

    public void setDiffuseCoefficient(double diffuseCoefficient) {
        this.diffuseCoefficient = diffuseCoefficient;
    }

    public void setSpecularCoefficient(double specularCoefficient) {
        this.specularCoefficient = specularCoefficient;
    }

    public void setSpecularExponent(double specularExponent) {
        this.specularExponent = specularExponent;
    }
}
