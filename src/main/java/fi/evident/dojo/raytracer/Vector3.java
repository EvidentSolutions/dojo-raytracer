package fi.evident.dojo.raytracer;

import org.jetbrains.annotations.NotNull;

import static fi.evident.dojo.raytracer.MathUtils.sqrt;
import static fi.evident.dojo.raytracer.MathUtils.square;

public final class Vector3 {

    @NotNull
    public static final Vector3 ZERO = new Vector3(0, 0, 0);

    public final float x;
    public final float y;
    public final float z;

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @NotNull
    public Vector3 scale(float s) {
        return new Vector3(s * x, s * y, s * z);
    }
    
    @NotNull
    public Vector3 divide(float s) {
        if (s == 0) throw new ArithmeticException("division by zero");
        
        return new Vector3(x / s, y / s, z / s);
    }
    
    @NotNull
    public Vector3 negate() {
        return scale(-1);
    }
    
    @NotNull
    public Vector3 add(@NotNull Vector3 v) {
        return new Vector3(x + v.x, y + v.y, z + v.z);
    }
    
    @NotNull
    public Vector3 subtract(@NotNull Vector3 v) {
        return new Vector3(x - v.x, y - v.y, z - v.z);
    }
    
    public float dotProduct(@NotNull Vector3 v) {
        return x*v.x + y*v.y + z*v.z;
    }
    
    @NotNull
    public Vector3 crossProduct(@NotNull Vector3 v) {
        return new Vector3(y*v.z - z*v.y, z*v.x - x*v.z, x*v.y - y*v.x);
    }

    @NotNull
    public Vector3 normalize() {
        return divide(magnitude());
    }

    public float magnitude() {
        return sqrt(magnitudeSquared());
    }
    
    public float magnitudeSquared() {
        return dotProduct(this);
    }
    
    public float distance(@NotNull Vector3 v) {
        float dx = x - v.x;
        float dy = y - v.y;
        float dz = z - v.z;
        return sqrt(square(dx) + square(dy) + square(dz));
    }
    
    @NotNull
    @Override
    public String toString() {
        return "[" + x + " " + y + " " + z + "]";
    }
}
