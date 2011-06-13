package fi.evident.dojo.raytracer;

public final class MathUtils {

    private static final float[] EMPTY_FLOAT_ARRAY = new float[0];
    
    public static float square(float x) {
        return x*x;
    }
    
    public static float sqrt(float x) {
        return (float) Math.sqrt(x);
    }
    
    public static float pow(float a, float b) {
        return (float) Math.pow(a, b);
    }
    
    /**
     * Returns the roots roots of ax^2+bx+c = 0.
     */
    public static float[] rootsOfQuadraticEquation(float a, float b, float c) {
        float disc = square(b) - 4 * a * c;
        float divisor = 2*a;
        
        if (disc < 0)
            return EMPTY_FLOAT_ARRAY;
        
        if (disc == 0)
            return new float[] { -b / divisor };
        
        float discSqrt = sqrt(disc);
        return new float[] { 
            (-b + discSqrt) / divisor,
            (-b - discSqrt) / divisor
        };
    }
}
