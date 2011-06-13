package fi.evident.dojo.raytracer;

public final class Raytracer {

    private final Scene scene;
    private final int width;
    private final int height;
    private final int maxDepth = 5;
    private final Color backgroundColor = Color.BLACK;
    private final Color maxDepthColor = new Color(0.5f, 0.5f, 0.5f);
    
    public Raytracer(Scene scene, int width, int height) {
        assert scene != null;
        assert width > 0;
        assert height > 0;
        
        this.scene = scene;
        this.width = width;
        this.height = height;
    }

    public Color colorFor(int x, int y) {
        float recenterY = -(y - (height / 2.0f)) / (2.0f * height);
        float recenterX = (x - (width / 2.0f)) / (2.0f * width);
        Vector3 direction = scene.camera.recenteredDirection(recenterX, recenterY);

        return traceRay(new Ray(scene.camera.position, direction), maxDepth);
    }

    /**
     * Traces a ray into given direction, taking at most maxSteps recursive
     * steps.
     */
    private Color traceRay(Ray ray, int maxSteps) {
        if (maxSteps == 0) return maxDepthColor;
        
        // TODO: implement the ray tracing algorithm
        return backgroundColor;
    }
}
