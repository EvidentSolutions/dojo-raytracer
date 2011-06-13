package fi.evident.dojo.raytracer;

import java.util.ArrayList;
import java.util.List;

public final class Scene {
    
    public final Camera camera;
    private final List<SceneObject> objects = new ArrayList<SceneObject>();
    private final List<Light> lights = new ArrayList<Light>();
    
    public Scene(Camera camera) {
        assert camera != null;
        
        this.camera = camera;
    }
    
    public Iterable<Light> getLights() {
        return lights;
    }
    
    public void addObject(SceneObject object) {
        assert object != null;
        
        objects.add(object);
    }
    
    public void addLight(Light light) {
        assert light != null;
        
        lights.add(light);
    }

    public void addLight(Vector3 position, Color color) {
        addLight(new Light(position, color));
    }
    
    public Intersection nearestIntersection(Ray ray) {
        Intersection nearest = null;
        
        for (SceneObject object : objects) {
            Intersection intersection = object.intersect(ray);
            if (intersection != null && (nearest == null || intersection.distance < nearest.distance))
                nearest = intersection;
        }
        
        return nearest;
    }
}
