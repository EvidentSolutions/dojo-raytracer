package fi.evident.dojo.raytracer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Intersection {
    
    @NotNull
    private final SceneObject object;

    @NotNull
    private final Ray ray;

    public float distance;

    @Nullable
    private Vector3 position;

    @Nullable
    private Vector3 normal;

    @Nullable
    private Vector3 reflectDirection;
    
    public Intersection(@NotNull SceneObject object, @NotNull Ray ray, float distance) {
        assert object != null;
        assert ray != null;
        assert distance >= 0;
        
        this.object = object;
        this.ray = ray;
        this.distance = distance;
    }

    @NotNull
    public Vector3 getPosition() {
        if (position == null)
            position = ray.start.add(ray.direction.scale(distance));
        return position;
    }

    @NotNull
    public Vector3 getNormal() {
        if (normal == null)
            normal = object.normal(getPosition());
        return normal;
    }

    @NotNull
    public Vector3 getReflectDirection() {
        if (reflectDirection == null) {
            Vector3 norm = getNormal();
            Vector3 dir = ray.direction;
            reflectDirection = dir.subtract(norm.scale(2 * norm.dotProduct(dir)));
        }
        return reflectDirection;  
    }

    @NotNull
    public Surface getSurface() {
        return object.surface;
    }
}
