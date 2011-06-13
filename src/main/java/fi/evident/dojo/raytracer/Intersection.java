package fi.evident.dojo.raytracer;

public final class Intersection {
    
    public final SceneObject object;
    public final Ray ray;
    public float distance;
    private Vector3 position;
    private Vector3 normal;
    private Vector3 reflectDirection;
    
    public Intersection(SceneObject object, Ray ray, float distance) {
        assert object != null;
        assert ray != null;
        assert distance >= 0;
        
        this.object = object;
        this.ray = ray;
        this.distance = distance;
    }

    public Vector3 getPosition() {
        if (position == null)
            position = ray.start.add(ray.direction.scale(distance));
        return position;
    }

    public Vector3 getNormal() {
        if (normal == null)
            normal = object.normal(getPosition());
        return normal;
    }
    
    public Vector3 getReflectDirection() {
        if (reflectDirection == null) {
            Vector3 norm = getNormal();
            Vector3 dir = ray.direction;
            reflectDirection = dir.subtract(norm.scale(2 * norm.dotProduct(dir)));
        }
        return reflectDirection;  
    }

    public Surface getSurface() {
        return object.surface;
    }
}
