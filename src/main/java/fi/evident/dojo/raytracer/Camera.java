package fi.evident.dojo.raytracer;

public final class Camera {
    
    public final Vector3 position;
    private final Vector3 forward;
    private final Vector3 up;
    private final Vector3 right;
    private static final Vector3 down = new Vector3(0, -1, 0); 
    
    public Camera(Vector3 position, Vector3 lookAt) {
        assert position != null;
        assert lookAt != null;
        
        this.forward = lookAt.subtract(position).normalize();
        this.right = forward.crossProduct(down).normalize().scale(1.5f);
        this.up = forward.crossProduct(right).normalize().scale(1.5f); 
        this.position = position;
    }

    public Vector3 recenteredDirection(float recenterX, float recenterY) {
        return forward.add(right.scale(recenterX).add(up.scale(recenterY))).normalize();
    }
}
