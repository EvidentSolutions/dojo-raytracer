package fi.evident.dojo.raytracer;

public abstract class SceneObject {
    
    public final Surface surface;
    
    protected SceneObject(Surface surface) {
        assert surface != null;
        
        this.surface = surface;
    }
    
    /**
     * Returns the intersection of this object with given ray, or null
     * if the ray does not intersect the object.
     */
    public abstract Intersection intersect(Ray ray);
    
    /**
     * Returns the normal of the object at given position.
     */
    public abstract Vector3 normal(Vector3 pos);
}
