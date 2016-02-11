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

import java.util.Optional;

public final class Plane extends SceneObject {

    @NotNull
    private final Vector3 normal;
    private final float offset;
    
    public Plane(@NotNull Vector3 normal, float offset, @NotNull Surface surface) {
        super(surface);

        this.normal = normal;
        this.offset = offset;
    }
    
    @NotNull
    @Override
    public Optional<Intersection> intersect(@NotNull Ray ray) {
        // See http://en.wikipedia.org/wiki/Line-plane_intersection
        float denom = normal.dotProduct(ray.direction);
        if (denom > 0) return Optional.empty();
        
        float distance = (normal.dotProduct(ray.start) + offset) / -denom; 
        return Optional.of(new Intersection(this, ray, distance));
    }
    
    @NotNull
    @Override
    public Vector3 normal(@NotNull Vector3 pos) {
        return normal;
    }
}
