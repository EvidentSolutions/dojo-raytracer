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

public final class Intersection {
    
    public final SceneObject object;
    public final Ray ray;
    public float distance;
    private Vector3 position;
    private Vector3 normal;
    private Vector3 reflectDirection;
    
    public Intersection(SceneObject object, Ray ray, float distance) {
        assert object != null;
        assert ray != null;
        assert distance >= 0;
        
        this.object = object;
        this.ray = ray;
        this.distance = distance;
    }

    public Vector3 getPosition() {
        if (position == null)
            position = ray.start.add(ray.direction.scale(distance));
        return position;
    }

    public Vector3 getNormal() {
        if (normal == null)
            normal = object.normal(getPosition());
        return normal;
    }
    
    public Vector3 getReflectDirection() {
        if (reflectDirection == null) {
            Vector3 norm = getNormal();
            Vector3 dir = ray.direction;
            reflectDirection = dir.subtract(norm.scale(2 * norm.dotProduct(dir)));
        }
        return reflectDirection;  
    }

    public Surface getSurface() {
        return object.surface;
    }
}
