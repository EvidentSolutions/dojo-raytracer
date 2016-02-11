package fi.evident.dojo.raytracer;

import org.jetbrains.annotations.NotNull;

public final class Light {
    
    @NotNull
    public final Vector3 position;

    @NotNull
    public final Color color;
    
    public Light(@NotNull Vector3 position, @NotNull Color color) {
        assert position != null;
        assert color != null;
        
        this.position = position;
        this.color = color;
    }

    @NotNull
    public Vector3 vectorFrom(@NotNull Vector3 pos) {
        return position.subtract(pos);
    }
}
