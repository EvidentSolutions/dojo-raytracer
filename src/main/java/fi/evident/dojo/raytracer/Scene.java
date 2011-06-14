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

import java.util.ArrayList;
import java.util.List;

public final class Scene {
    
    public final Camera camera;
    private final List<SceneObject> objects = new ArrayList<SceneObject>();
    private final List<Light> lights = new ArrayList<Light>();
    
    public Scene(Camera camera) {
        assert camera != null;
        
        this.camera = camera;
    }
    
    public Iterable<Light> getLights() {
        return lights;
    }
    
    public void addObject(SceneObject object) {
        assert object != null;
        
        objects.add(object);
    }
    
    public void addLight(Light light) {
        assert light != null;
        
        lights.add(light);
    }

    public void addLight(Vector3 position, Color color) {
        addLight(new Light(position, color));
    }
    
    public Intersection nearestIntersection(Ray ray) {
        Intersection nearest = null;
        
        for (SceneObject object : objects) {
            Intersection intersection = object.intersect(ray);
            if (intersection != null && (nearest == null || intersection.distance < nearest.distance))
                nearest = intersection;
        }
        
        return nearest;
    }
}
