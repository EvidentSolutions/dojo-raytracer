package fi.evident.dojo.raytracer;

import static java.lang.Math.floor;

public final class Surfaces {

    public static final Surface checkerboard = new Surface(150) {
        @Override
        public Color diffuse(Vector3 pos) {
            return ((floor(pos.z) + floor(pos.x)) % 2 != 0) ? Color.WHITE : Color.BLACK;
        }

        @Override
        public Color specular(Vector3 pos) {
            return Color.WHITE;
        }

        @Override
        public float reflectivity(Vector3 pos) {
            return ((floor(pos.z) + floor(pos.x)) % 2 != 0) ? 0.1f : 0.7f;
        }
    };
   
    public static final Surface shiny = new Surface(50) {
        
        @Override
        public Color specular(Vector3 pos) {
            return new Color(0.5f, 0.5f, 0.5f);
        }
  
        @Override
        public Color diffuse(Vector3 pos) {
            return Color.WHITE;
        }
  
        @Override
        public float reflectivity(Vector3 pos) {
            return 0.6f;
        }
    };
    
    public static final Surface mirror = new Surface(50) {
        @Override
        public Color specular(Vector3 pos) {
            return Color.BLACK;
        }
  
        @Override
        public Color diffuse(Vector3 pos) {
            return Color.BLACK;
        }
  
        @Override
        public float reflectivity(Vector3 pos) {
            return 1.0f;
        }
    };

    public static Surface findSurfaceByName(String name) {
        try {
            return (Surface) Surfaces.class.getField(name).get(null);
        } catch (NoSuchFieldException e) {
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
