package fi.evident.dojo.raytracer;

import org.jetbrains.annotations.NotNull;

public abstract class Surface {
    
    public final float roughness;
    
    protected Surface(float roughness) {
        assert roughness > 0;
        
        this.roughness = roughness;
    }

    @NotNull
    public abstract Color diffuse(@NotNull Vector3 pos);

    @NotNull
    public abstract Color specular(@NotNull Vector3 pos);

    public abstract float reflectivity(@NotNull Vector3 pos);
}
