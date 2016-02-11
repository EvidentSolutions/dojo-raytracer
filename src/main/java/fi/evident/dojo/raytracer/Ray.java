package fi.evident.dojo.raytracer;

import org.jetbrains.annotations.NotNull;

public final class Ray {

    @NotNull
    public final Vector3 start;

    @NotNull
    public final Vector3 direction;

    public Ray(@NotNull Vector3 start, @NotNull Vector3 direction) {
        this.start = start;
        this.direction = direction;
    }
}
