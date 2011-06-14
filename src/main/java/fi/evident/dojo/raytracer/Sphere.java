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

public final class Sphere extends SceneObject {

    private final Vector3 center;
    private final float radius;
    
    public Sphere(Vector3 center, float radius, Surface surface) {
        super(surface);
        
        assert center != null;
        assert radius > 0;
        
        this.center = center;
        this.radius = radius;
    }
    
    @Override
    public Intersection intersect(Ray ray) {
        // See http://en.wikipedia.org/wiki/Line-sphere_intersection
        Vector3 v = center.subtract(ray.start);
        float b = v.dotProduct(ray.direction);

        // if v < 0, distance is going to be negative; bail out early
        if (b < 0)
            return null;
        
        float disc = square(radius) - (v.magnitudeSquared() - square(b));
        if (disc < 0)
            return null;
        
        float distance = b - sqrt(disc);
        if (distance <= 0)
            return null;
        
        return new Intersection(this, ray, distance); 
    }
    
    @Override
    public Vector3 normal(Vector3 pos) {
        return pos.subtract(center).normalize();
    }
}
