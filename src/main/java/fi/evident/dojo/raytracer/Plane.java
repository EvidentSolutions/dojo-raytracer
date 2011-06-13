package fi.evident.dojo.raytracer;

public final class Plane extends SceneObject {

    private final Vector3 normal;
    private final float offset;
    
    public Plane(Vector3 normal, float offset, Surface surface) {
        super(surface);
        
        assert surface != null;
        
        this.normal = normal;
        this.offset = offset;
    }
    
    @Override
    public Intersection intersect(Ray ray) {
        // See http://en.wikipedia.org/wiki/Line-plane_intersection
        float denom = normal.dotProduct(ray.direction);
        if (denom > 0) return null;
        
        float distance = (normal.dotProduct(ray.start) + offset) / -denom; 
        return new Intersection(this, ray, distance);
    }
    
    @Override
    public Vector3 normal(Vector3 pos) {
        return normal;
    }
}
