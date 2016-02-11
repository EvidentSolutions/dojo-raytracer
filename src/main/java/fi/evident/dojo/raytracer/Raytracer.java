package fi.evident.dojo.raytracer;

import org.jetbrains.annotations.NotNull;

import static fi.evident.dojo.raytracer.MathUtils.pow;

public final class Raytracer {

    @NotNull
    private final Scene scene;

    private final int width;

    private final int height;

    @NotNull
    private final Color backgroundColor = Color.BLACK;

    @NotNull
    private final Color maxDepthColor = new Color(0.5f, 0.5f, 0.5f);

    private static final int MAX_DEPTH = 5;

    public Raytracer(@NotNull Scene scene, int width, int height) {
        assert width > 0;
        assert height > 0;
        
        this.scene = scene;
        this.width = width;
        this.height = height;
    }

    @NotNull
    public Color colorFor(int x, int y) {
        float recenterY = -(y - (height / 2.0f)) / (2.0f * height);
        float recenterX = (x - (width / 2.0f)) / (2.0f * width);
        Vector3 direction = scene.camera.recenteredDirection(recenterX, recenterY);

        return traceRay(new Ray(scene.camera.position, direction), MAX_DEPTH);
    }

    /**
     * Traces a ray into given direction, taking at most maxSteps recursive
     * steps.
     */
    @NotNull
    private Color traceRay(@NotNull Ray ray, int maxSteps) {
        Intersection intersection = scene.nearestIntersection(ray).orElse(null);
        if (intersection == null)
            return backgroundColor;
        
        Color naturalColor = naturalColor(intersection);
        Color reflectColor = reflectColor(intersection, maxSteps);
        
        return naturalColor.add(reflectColor);
    }

    /**
     * Returns the natural color of given intersection by sum of different
     * lights coming to that position.
     */
    @NotNull
    private Color naturalColor(@NotNull Intersection intersection) {
        Color color = Color.BLACK;

        for (Light light : scene.getLights())
            color = color.add(naturalColor(intersection, light));
        
        return color;
    }

    /**
     * Returns the natural color given by given light.
     */
    @NotNull
    private Color naturalColor(@NotNull Intersection intersection, @NotNull Light light) {
        if (isInShadow(light, intersection.getPosition())) {
            return Color.BLACK;
        } else {
            Color diffuseColor = diffuseColor(intersection, light);
            Color specularColor = specularColor(intersection, light);

            return diffuseColor.add(specularColor);
        }
    }

    /**
     * Calculates the color which is reflected to given intersection by
     * recursively tracing ray towards the direction of reflection.
     */
    @NotNull
    private Color reflectColor(@NotNull Intersection intersection, int maxSteps) {
        if (maxSteps <= 1)
            return maxDepthColor;
        
        Vector3 reflectDir = intersection.getReflectDirection();
        Vector3 reflectPos = intersection.getPosition().add(reflectDir.scale(0.001f));

        float reflectivity = intersection.getSurface().reflectivity(reflectPos);
        if (reflectivity == 0)
            return Color.BLACK;
        
        Color color = traceRay(new Ray(reflectPos, reflectDir), maxSteps-1);
        return color.multiply(reflectivity);
    }
    
    /**
     * Calculates the diffuse color at given intersection by Lambertian
     * reflectance. See http://en.wikipedia.org/wiki/Lambertian_reflectance
     */
    @NotNull
    private Color diffuseColor(@NotNull Intersection intersection, @NotNull Light light) {
        Vector3 pos = intersection.getPosition();
        Vector3 lightDirection = light.vectorFrom(pos).normalize();

        float illumination = lightDirection.dotProduct(intersection.getNormal());
        if (illumination <= 0)
            return Color.BLACK;
        
        Surface surface = intersection.getSurface();
        Color color = light.color.multiply(illumination);
        return color.multiply(surface.diffuse(pos));
    }

    /**
     * Calculates the specular reflection of light at given intersection.
     * See http://en.wikipedia.org/wiki/Phong_shading
     * and http://en.wikipedia.org/wiki/Specular_reflection
     */
    @NotNull
    private Color specularColor(@NotNull Intersection intersection, @NotNull Light light) {
        Vector3 pos = intersection.getPosition();
        Vector3 vectorToLight = light.vectorFrom(pos).normalize();
        Vector3 reflectDir = intersection.getReflectDirection();
        
        float specular = vectorToLight.dotProduct(reflectDir.normalize());
        if (specular <= 0)
            return Color.BLACK;

        Surface surface = intersection.getSurface();
        float specularFactor = pow(specular, surface.roughness);
        Color color = light.color.multiply(specularFactor);
        
        return color.multiply(surface.specular(pos));
    }

    /**
     * Returns true if given light is not visible from given position.
     */
    private boolean isInShadow(@NotNull Light light, @NotNull Vector3 pos) {
        Vector3 vectorToLight = light.vectorFrom(pos);
        Ray testRay = new Ray(pos, vectorToLight.normalize());

        Intersection intersection = scene.nearestIntersection(testRay).orElse(null);
        return (intersection != null) && (intersection.distance <= vectorToLight.magnitude());
    }
}
