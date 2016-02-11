package fi.evident.dojo.raytracer;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

import static fi.evident.dojo.raytracer.MathUtils.sqrt;
import static fi.evident.dojo.raytracer.MathUtils.square;

public final class Sphere extends SceneObject {

    @NotNull
    private final Vector3 center;
    private final float radius;
    
    public Sphere(@NotNull Vector3 center, float radius, @NotNull Surface surface) {
        super(surface);
        
        assert center != null;
        assert radius > 0;
        
        this.center = center;
        this.radius = radius;
    }
    
    @NotNull
    @Override
    public Optional<Intersection> intersect(@NotNull Ray ray) {
        // See http://en.wikipedia.org/wiki/Line-sphere_intersection
        Vector3 v = center.subtract(ray.start);
        float b = v.dotProduct(ray.direction);

        // if v < 0, distance is going to be negative; bail out early
        if (b < 0)
            return Optional.empty();
        
        float disc = square(radius) - (v.magnitudeSquared() - square(b));
        if (disc < 0)
            return Optional.empty();
        
        float distance = b - sqrt(disc);
        if (distance <= 0)
            return Optional.empty();
        
        return Optional.of(new Intersection(this, ray, distance));
    }
    
    @Override
    @NotNull
    public Vector3 normal(@NotNull Vector3 pos) {
        return pos.subtract(center).normalize();
    }
}
