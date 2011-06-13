package fi.evident.dojo.raytracer;

import static fi.evident.dojo.raytracer.MathUtils.sqrt;
import static fi.evident.dojo.raytracer.MathUtils.square;

public final class Sphere extends SceneObject {

    private final Vector3 center;
    private final float radius;
    
    public Sphere(Vector3 center, float radius, Surface surface) {
        super(surface);
        
        assert center != null;
        assert radius > 0;
        
        this.center = center;
        this.radius = radius;
    }
    
    @Override
    public Intersection intersect(Ray ray) {
        // See http://en.wikipedia.org/wiki/Line-sphere_intersection
        Vector3 v = center.subtract(ray.start);
        float b = v.dotProduct(ray.direction);

        // if v < 0, distance is going to be negative; bail out early
        if (b < 0)
            return null;
        
        float disc = square(radius) - (v.magnitudeSquared() - square(b));
        if (disc < 0)
            return null;
        
        float distance = b - sqrt(disc);
        if (distance <= 0)
            return null;
        
        return new Intersection(this, ray, distance); 
    }
    
    @Override
    public Vector3 normal(Vector3 pos) {
        return pos.subtract(center).normalize();
    }
}
