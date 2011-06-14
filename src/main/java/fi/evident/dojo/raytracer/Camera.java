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

public final class Camera {
    
    public final Vector3 position;
    private final Vector3 forward;
    private final Vector3 up;
    private final Vector3 right;
    private static final Vector3 down = new Vector3(0, -1, 0); 
    
    public Camera(Vector3 position, Vector3 lookAt) {
        assert position != null;
        assert lookAt != null;
        
        this.forward = lookAt.subtract(position).normalize();
        this.right = forward.crossProduct(down).normalize().scale(1.5f);
        this.up = forward.crossProduct(right).normalize().scale(1.5f); 
        this.position = position;
    }

    public Vector3 recenteredDirection(float recenterX, float recenterY) {
        return forward.add(right.scale(recenterX).add(up.scale(recenterY))).normalize();
    }
}
