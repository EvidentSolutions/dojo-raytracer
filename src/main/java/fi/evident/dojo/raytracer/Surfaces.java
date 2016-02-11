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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.lang.Math.floor;

public final class Surfaces {

    @NotNull
    private static final Map<String, Surface> surfaces = initSurfaces();

    @NotNull
    public static Optional<Surface> findSurfaceByName(@NotNull String name) {
        return Optional.ofNullable(surfaces.get(name));
    }

    @NotNull
    private static Map<String, Surface> initSurfaces() {
        Map<String, Surface> surfaces = new HashMap<>();

        surfaces.put("checkerboard", createCheckerboardSurface());
        surfaces.put("shiny", createShinySurface());
        surfaces.put("mirror", createMirrorSurface());

        return surfaces;
    }

    @NotNull
    private static Surface createCheckerboardSurface() {
        return new Surface(150) {
            @NotNull
            @Override
            public Color diffuse(@NotNull Vector3 pos) {
                return ((floor(pos.z) + floor(pos.x)) % 2 != 0) ? Color.WHITE : Color.BLACK;
            }

            @NotNull
            @Override
            public Color specular(@NotNull Vector3 pos) {
                return Color.WHITE;
            }

            @Override
            public float reflectivity(@NotNull Vector3 pos) {
                return ((floor(pos.z) + floor(pos.x)) % 2 != 0) ? 0.1f : 0.7f;
            }
        };
    }

    @NotNull
    private static Surface createShinySurface() {
        return new Surface(50) {

            @NotNull
            @Override
            public Color specular(@NotNull Vector3 pos) {
                return new Color(0.5f, 0.5f, 0.5f);
            }

            @NotNull
            @Override
            public Color diffuse(@NotNull Vector3 pos) {
                return Color.WHITE;
            }

            @Override
            public float reflectivity(@NotNull Vector3 pos) {
                return 0.6f;
            }
        };
    }

    @NotNull
    private static Surface createMirrorSurface() {
        return new Surface(50) {
            @NotNull
            @Override
            public Color specular(@NotNull Vector3 pos) {
                return Color.BLACK;
            }

            @NotNull
            @Override
            public Color diffuse(@NotNull Vector3 pos) {
                return Color.BLACK;
            }

            @Override
            public float reflectivity(@NotNull Vector3 pos) {
                return 1.0f;
            }
        };
    }
}
