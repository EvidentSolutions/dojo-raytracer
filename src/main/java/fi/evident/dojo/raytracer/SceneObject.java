package fi.evident.dojo.raytracer;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public abstract class SceneObject {
    
    @NotNull
    public final Surface surface;
    
    protected SceneObject(@NotNull Surface surface) {
        this.surface = surface;
    }
    
    /**
     * Returns the intersection of this object with given ray, or empty
     * if the ray does not intersect the object.
     */
    @NotNull
    public abstract Optional<Intersection> intersect(@NotNull Ray ray);
    
    /**
     * Returns the normal of the object at given position.
     */
    @NotNull
    public abstract Vector3 normal(@NotNull Vector3 pos);
}
