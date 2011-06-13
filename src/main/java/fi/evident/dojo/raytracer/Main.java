package fi.evident.dojo.raytracer;

import javax.swing.*;

public class Main {
    
    public static void main(String[] args) {
        try {
            String sceneFile = 
                (args.length == 1) ? args[0] : "scenes/simple.scene";
            Scene scene = SceneParser.parse(sceneFile);
            
            JFrame frame = new JFrame("raytracer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
            RaytracerView view = new RaytracerView(scene);
            
            frame.add(view);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            
            view.startRaytracing();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
