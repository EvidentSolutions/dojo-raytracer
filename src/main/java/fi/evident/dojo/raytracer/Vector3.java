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

import static fi.evident.dojo.raytracer.MathUtils.sqrt;
import static fi.evident.dojo.raytracer.MathUtils.square;

public final class Vector3 {

    public static final Vector3 ZERO = new Vector3(0, 0, 0);

    public final float x;
    public final float y;
    public final float z;

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3 scale(float s) {
        return new Vector3(s * x, s * y, s * z);
    }
    
    public Vector3 divide(float s) {
        if (s == 0) throw new ArithmeticException("division by zero");
        
        return new Vector3(x / s, y / s, z / s);
    }
    
    public Vector3 negate() {
        return scale(-1);
    }
    
    public Vector3 add(Vector3 v) {
        return new Vector3(x + v.x, y + v.y, z + v.z);
    }
    
    public Vector3 subtract(Vector3 v) {
        return new Vector3(x - v.x, y - v.y, z - v.z);
    }
    
    public float dotProduct(Vector3 v) {
        return x*v.x + y*v.y + z*v.z;
    }
    
    public Vector3 crossProduct(Vector3 v) {
        return new Vector3(y*v.z - z*v.y, z*v.x - x*v.z, x*v.y - y*v.x);
    }

    public Vector3 normalize() {
        return divide(magnitude());
    }

    public float magnitude() {
        return sqrt(magnitudeSquared());
    }
    
    public float magnitudeSquared() {
        return dotProduct(this);
    }
    
    public float distance(Vector3 v) {
        float dx = x - v.x;
        float dy = y - v.y;
        float dz = z - v.z;
        return sqrt(square(dx) + square(dy) + square(dz));
    }
    
    @Override
    public String toString() {
        return "[" + x + " " + y + " " + z + "]";
    }
}
