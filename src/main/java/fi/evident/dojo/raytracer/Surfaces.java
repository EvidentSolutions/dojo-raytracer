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

import static java.lang.Math.floor;

public final class Surfaces {

    public static final Surface checkerboard = new Surface(150) {
        @Override
        public Color diffuse(Vector3 pos) {
            return ((floor(pos.z) + floor(pos.x)) % 2 != 0) ? Color.WHITE : Color.BLACK;
        }

        @Override
        public Color specular(Vector3 pos) {
            return Color.WHITE;
        }

        @Override
        public float reflectivity(Vector3 pos) {
            return ((floor(pos.z) + floor(pos.x)) % 2 != 0) ? 0.1f : 0.7f;
        }
    };
   
    public static final Surface shiny = new Surface(50) {
        
        @Override
        public Color specular(Vector3 pos) {
            return new Color(0.5f, 0.5f, 0.5f);
        }
  
        @Override
        public Color diffuse(Vector3 pos) {
            return Color.WHITE;
        }
  
        @Override
        public float reflectivity(Vector3 pos) {
            return 0.6f;
        }
    };
    
    public static final Surface mirror = new Surface(50) {
        @Override
        public Color specular(Vector3 pos) {
            return Color.BLACK;
        }
  
        @Override
        public Color diffuse(Vector3 pos) {
            return Color.BLACK;
        }
  
        @Override
        public float reflectivity(Vector3 pos) {
            return 1.0f;
        }
    };

    public static Surface findSurfaceByName(String name) {
        try {
            return (Surface) Surfaces.class.getField(name).get(null);
        } catch (NoSuchFieldException e) {
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
