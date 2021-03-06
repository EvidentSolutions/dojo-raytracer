package fi.evident.dojo.raytracer;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class Scene {
    
    @NotNull
    public final Camera camera;

    @NotNull
    private final List<SceneObject> objects = new ArrayList<>();

    @NotNull
    private final List<Light> lights = new ArrayList<>();
    
    public Scene(@NotNull Camera camera) {
        this.camera = camera;
    }
    
    @NotNull
    public Iterable<Light> getLights() {
        return lights;
    }
    
    public void addObject(@NotNull SceneObject object) {
        objects.add(object);
    }
    
    public void addLight(@NotNull Light light) {
        lights.add(light);
    }

    public void addLight(@NotNull Vector3 position, @NotNull Color color) {
        addLight(new Light(position, color));
    }

    @NotNull
    public Optional<Intersection> nearestIntersection(@NotNull Ray ray) {
        Intersection nearest = null;
        
        for (SceneObject object : objects) {
            Intersection intersection = object.intersect(ray).orElse(null);
            if (intersection != null && (nearest == null || intersection.distance < nearest.distance))
                nearest = intersection;
        }
        
        return Optional.ofNullable(nearest);
    }
}
