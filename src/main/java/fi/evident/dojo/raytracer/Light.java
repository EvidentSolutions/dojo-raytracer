package fi.evident.dojo.raytracer;

public final class Light {
    
    public final Vector3 position;
    public final Color color;
    
    public Light(Vector3 position, Color color) {
        assert position != null;
        assert color != null;
        
        this.position = position;
        this.color = color;
    }
    
    public Vector3 vectorFrom(Vector3 pos) {
        return position.subtract(pos);
    }
}
