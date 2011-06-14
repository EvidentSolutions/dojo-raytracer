/*
 * Copyright (c) 2011 Evident Solutions Oy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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
