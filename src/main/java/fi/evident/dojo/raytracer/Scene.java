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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class Scene {
    
    @NotNull
    public final Camera camera;

    @NotNull
    private final List<SceneObject> objects = new ArrayList<>();

    @NotNull
    private final List<Light> lights = new ArrayList<>();
    
    public Scene(@NotNull Camera camera) {
        this.camera = camera;
    }
    
    @NotNull
    public Iterable<Light> getLights() {
        return lights;
    }
    
    public void addObject(@NotNull SceneObject object) {
        objects.add(object);
    }
    
    public void addLight(@NotNull Light light) {
        lights.add(light);
    }

    public void addLight(@NotNull Vector3 position, @NotNull Color color) {
        addLight(new Light(position, color));
    }

    @NotNull
    public Optional<Intersection> nearestIntersection(@NotNull Ray ray) {
        Intersection nearest = null;
        
        for (SceneObject object : objects) {
            Intersection intersection = object.intersect(ray).orElse(null);
            if (intersection != null && (nearest == null || intersection.distance < nearest.distance))
                nearest = intersection;
        }
        
        return Optional.ofNullable(nearest);
    }
}
