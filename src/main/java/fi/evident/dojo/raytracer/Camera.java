package fi.evident.dojo.raytracer;

import org.jetbrains.annotations.NotNull;

public final class Camera {
    
    @NotNull
    public final Vector3 position;

    @NotNull
    private final Vector3 forward;

    @NotNull
    private final Vector3 up;

    @NotNull
    private final Vector3 right;

    @NotNull
    private static final Vector3 down = new Vector3(0, -1, 0);
    
    public Camera(@NotNull Vector3 position, @NotNull Vector3 lookAt) {
        this.forward = lookAt.subtract(position).normalize();
        this.right = forward.crossProduct(down).normalize().scale(1.5f);
        this.up = forward.crossProduct(right).normalize().scale(1.5f); 
        this.position = position;
    }

    @NotNull
    public Vector3 recenteredDirection(float recenterX, float recenterY) {
        return forward.add(right.scale(recenterX).add(up.scale(recenterY))).normalize();
    }
}
