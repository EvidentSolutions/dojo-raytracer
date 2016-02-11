package fi.evident.dojo.raytracer;

import org.jetbrains.annotations.NotNull;

import static java.lang.Math.max;
import static java.lang.Math.min;

public final class Color {

    @NotNull
    public static final Color BLACK = new Color(0, 0, 0);

    @NotNull
    public static final Color WHITE = new Color(1, 1, 1);
    
    private final float r;
    private final float g;
    private final float b;

    public Color(float r, float g, float b) {
        this.r = r; 
        this.g = g; 
        this.b = b;
    }

    @NotNull
    public Color multiply(float n) {
        return new Color(n*r, n*g, n*b);
    }

    @NotNull
    public Color multiply(@NotNull Color c) {
        return new Color(r*c.r, g*c.g, b*c.b);
    }

    @NotNull
    public Color add(@NotNull Color c) {
        return new Color(r+c.r, g+c.g, b+c.b);
    }
    
    @NotNull
    public Color subtract(@NotNull Color c) {
        return new Color(r-c.r, g-c.g, b-c.b);
    }

    public int toARGB() {
        int aa = 0xFF;
        int rr = (int) (clamp(r)*255+0.5);
        int gg = (int) (clamp(g)*255+0.5);
        int bb = (int) (clamp(b)*255+0.5);
            
        return ((aa & 0xFF) << 24) |
               ((rr & 0xFF) << 16) |
               ((gg & 0xFF) << 8)  |
               ((bb & 0xFF));
    }
    
    private static float clamp(float d) {
        return max(0, min(d, 1));
    }
    
    @NotNull
    @Override
    public String toString() {
        return "(" + r + " " + g + " " + b + ")";
    }
}

