package world.entities;

import utils.Color;
import utils.Ray;
import utils.Vector3D;
import world.IntersectData;
import world.Light;
import world.World;

import java.util.List;

/**
 * An entity that can be rendered and hit by rays in teh world
 */
public abstract class Entity implements Shape3D {

    protected ReflectiveProperties reflectiveProperties;

    public Entity(){}

    public Entity(ReflectiveProperties reflectiveProperties) {
        this.reflectiveProperties = reflectiveProperties;
    }

    public ReflectiveProperties getReflectiveProperties() {
        return reflectiveProperties;
    }

    public Color getColor(World world, IntersectData intersect, List<Light> visibleLights){
        Color ambientColor = new Color(
                world.getAmbientColor()
                        .multiplyColor(reflectiveProperties.getAmbDiffColor())
                        .scalarMultiply(reflectiveProperties.getAmbientCoefficient())
        );

        Color diffuseColor = new Color(0,0,0);
        Color specularColor = new Color(0,0,0);
        for(Light light: visibleLights){
            Color lightColor = light.getColor();
            Vector3D toLight = light.getPosition().subtract(intersect.point).normal();
            Color diffuse = new Color(
                    lightColor.multiplyColor(reflectiveProperties.getAmbDiffColor())
                    .scalarMultiply(toLight.dot(intersect.normal))
            );

            diffuseColor = new Color(diffuseColor.add(diffuse));

            Vector3D reflect = Vector3D.incidence(toLight.scalarMultiply(-1), intersect.normal);
            double reflectLookAtDot = intersect.lookAt.scalarMultiply(-1).dot(reflect);

            if(reflectLookAtDot > 0) {
                Color specular = new Color(
                        lightColor.multiplyColor(reflectiveProperties.getSpecColor())
                                .scalarMultiply(
                                        Math.pow(reflectLookAtDot, reflectiveProperties.getSpecularExponent())
                                )
                );

                specularColor = new Color(specularColor.add(specular));
            }
        }
        return new Color( ambientColor
                .add(diffuseColor.scalarMultiply(reflectiveProperties.getDiffuseCoefficient()))
                .add(specularColor.scalarMultiply(reflectiveProperties.getSpecularCoefficient()))
        );
    }
}
