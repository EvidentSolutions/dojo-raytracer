package fi.evident.dojo.raytracer;

public final class Ray {

    public final Vector3 start;
    public  final Vector3 direction;

    public Ray(Vector3 start, Vector3 direction) {
        assert start != null;
        assert direction != null;
        
        this.start = start;
        this.direction = direction;
    }
}
