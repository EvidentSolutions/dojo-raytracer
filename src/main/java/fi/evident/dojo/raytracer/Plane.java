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
