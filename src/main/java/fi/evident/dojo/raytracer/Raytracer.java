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

public final class Raytracer {

    private final Scene scene;
    private final int width;
    private final int height;
    private final int maxDepth = 5;
    private final Color backgroundColor = Color.BLACK;
    private final Color maxDepthColor = new Color(0.5f, 0.5f, 0.5f);
    
    public Raytracer(Scene scene, int width, int height) {
        assert scene != null;
        assert width > 0;
        assert height > 0;
        
        this.scene = scene;
        this.width = width;
        this.height = height;
    }

    public Color colorFor(int x, int y) {
        float recenterY = -(y - (height / 2.0f)) / (2.0f * height);
        float recenterX = (x - (width / 2.0f)) / (2.0f * width);
        Vector3 direction = scene.camera.recenteredDirection(recenterX, recenterY);

        return traceRay(new Ray(scene.camera.position, direction), maxDepth);
    }

    /**
     * Traces a ray into given direction, taking at most maxSteps recursive
     * steps.
     */
    private Color traceRay(Ray ray, int maxSteps) {
        if (maxSteps == 0) return maxDepthColor;
        
        // TODO: implement the ray tracing algorithm
        return backgroundColor;
    }
}