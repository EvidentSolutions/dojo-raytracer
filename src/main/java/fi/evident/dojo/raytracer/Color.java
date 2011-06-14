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

import static java.lang.Math.max;
import static java.lang.Math.min;

public final class Color {
    
    public static final Color BLACK = new Color(0, 0, 0);
    public static final Color WHITE = new Color(1, 1, 1);
    
    private final float r;
    private final float g;
    private final float b;

    public Color(float r, float g, float b) {
        this.r = r; 
        this.g = g; 
        this.b = b;
    }

    public Color multiply(float n) {
        return new Color(n*r, n*g, n*b);
    }

    public Color multiply(Color c) {
        return new Color(r*c.r, g*c.g, b*c.b);
    }

    public Color add(Color c) {
        return new Color(r+c.r, g+c.g, b+c.b);
    }
    
    public Color subtract(Color c) {
        return new Color(r-c.r, g-c.g, b-c.b);
    }

    public int toARGB() {
        int aa = 0xFF;
        int rr = (int) (clamp(r)*255+0.5);
        int gg = (int) (clamp(g)*255+0.5);
        int bb = (int) (clamp(b)*255+0.5);
            
        return ((aa & 0xFF) << 24) |
               ((rr & 0xFF) << 16) |
               ((gg & 0xFF) << 8)  |
               ((bb & 0xFF) << 0);
    }
    
    private static float clamp(float d) {
        return max(0, min(d, 1));
    }
    
    @Override
    public String toString() {
        return "(" + r + " " + g + " " + b + ")";
    }
}

