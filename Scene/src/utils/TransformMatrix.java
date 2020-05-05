package utils;

import java.util.concurrent.ThreadLocalRandom;

public class TransformMatrix {
    private double[][] matrix;
    private TransformMatrix(double[][] matrix){
        this.matrix = matrix;
    }

    public static TransformMatrix rotateAroundAxis(Vector3D axis, double angle){
        Vector3D unitAxis = axis.normal();
        double x = unitAxis.getX();
        double y = unitAxis.getY();
        double z = unitAxis.getZ();
        double projectionOntoYZ = Math.sqrt( y * y + z * z );
        double[][] rotateToXZMatrix = {
                {1,0,0,0},
                {0, z/projectionOntoYZ, -y/projectionOntoYZ,0},
                {0, y/projectionOntoYZ, z/projectionOntoYZ, 0},
                {0,0,0,1}
        };
        double[][] inverseRotateToXZMatrix = {
                {1,0,0,0},
                {0, z/projectionOntoYZ, y/projectionOntoYZ,0},
                {0, -y/projectionOntoYZ, z/projectionOntoYZ, 0},
                {0,0,0,1}
        };

        TransformMatrix rotateToXZ = new TransformMatrix(rotateToXZMatrix);
        TransformMatrix inverseRotateToXZ = new TransformMatrix(inverseRotateToXZMatrix);

        double[][] rotateToZMatrix = {
                {projectionOntoYZ, 0, -x, 0},
                {0,1,0,0},
                {x,0,projectionOntoYZ,0},
                {0,0,0,1}
        };
        double[][] inverseRotateToZMatrix = {
                {projectionOntoYZ, 0, x, 0},
                {0,1,0,0},
                {-x,0,projectionOntoYZ,0},
                {0,0,0,1}
        };

        TransformMatrix rotateToZ = new TransformMatrix(rotateToZMatrix);
        TransformMatrix inverseRotateToZ = new TransformMatrix(inverseRotateToZMatrix);

        double[][] rotateAboutZMatrix = {
                {Math.cos(angle),-Math.sin(angle),0,0},
                {Math.sin(angle), Math.cos(angle),0,0},
                {0,0,1,0},
                {0,0,0,1}
        };

        TransformMatrix rotateAboutZ = new TransformMatrix(rotateAboutZMatrix);

        return inverseRotateToXZ
                .leftMultiply(inverseRotateToZ)
                .leftMultiply(rotateAboutZ)
                .leftMultiply(rotateToZ)
                .leftMultiply(rotateToXZ);

    }

    public static TransformMatrix translateTransformAndBack(Vector3D displacement, TransformMatrix transform){
        double[][] translateMatrix = {
                {1,0,0,displacement.getX()},
                {0,1,0,displacement.getY()},
                {0,0,1,displacement.getZ()},
                {0,0,0,1}
        };
        double[][] inverseTranslateMatrix = {
                {1,0,0,-displacement.getX()},
                {0,1,0,-displacement.getY()},
                {0,0,1,-displacement.getZ()},
                {0,0,0,1}
        };

        TransformMatrix translate = new TransformMatrix(translateMatrix);
        TransformMatrix inverseTranslate = new TransformMatrix(inverseTranslateMatrix);

        return inverseTranslate.leftMultiply(transform).leftMultiply(translate);
    }

    public TransformMatrix leftMultiply(TransformMatrix rightMatrix){
        double[][] result = new double[4][4];
        for(int row = 0; row < 4; row++){
            for(int col = 0; col < 4; col++){
                double value = 0;
                for(int i = 0; i < 4; i++){
                    value += matrix[row][i] * rightMatrix.matrix[i][col];
                }
                result[row][col] = value;
            }
        }
        return new TransformMatrix(result);
    }

    public Vector3D leftMultiply(Vector3D vector3D){
        double[] result = new double[3];
        for(int i = 0; i < 3; i++) {
            result[i] = matrix[i][0] * vector3D.getX()
                    + matrix[i][1] * vector3D.getY()
                    + matrix[i][2] * vector3D.getZ()
                    + matrix[i][3];
        }
        return new Vector3D(result[0], result[1], result[2]);
    }

}
