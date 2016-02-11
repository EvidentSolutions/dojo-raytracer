package fi.evident.dojo.raytracer;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class RaytracerView extends JComponent {

    @NotNull
    private final BufferedImage image = new BufferedImage(600, 600, BufferedImage.TYPE_INT_RGB);

    @NotNull
    private final Raytracer raytracer;
    
    public RaytracerView(@NotNull Scene scene) {
        raytracer = new Raytracer(scene, image.getWidth(), image.getHeight());        
    }
    
    @Override
    protected void paintComponent(@NotNull Graphics g) {
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
    
    @NotNull
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(image.getWidth(), image.getHeight());
    }
    
    public void startRaytracing() {
        int numberOfThreads = Runtime.getRuntime().availableProcessors();
        final int repaintInterval = 20;
        final CountDownLatch latch = new CountDownLatch(numberOfThreads);
        final AtomicInteger row = new AtomicInteger(0);
        
        for (int i = 0; i < numberOfThreads; i++)
            new Thread(() -> {
                int width1 = image.getWidth();
                int height1 = image.getHeight();
                int[] rgbArray = new int[width1];

                int y1;
                while ((y1 = row.incrementAndGet()) < height1) {
                    for (int x1 = 0; x1 < width1; x1++)
                        rgbArray[x1] = raytracer.colorFor(x1, y1).toARGB();

                    image.setRGB(0, y1, width1, 1, rgbArray, 0, 1);

                    if ((y1 % repaintInterval) == 0)
                        repaint();
                }

                latch.countDown();
           }).start();
        
        new Thread(() -> {
            long startTime = System.currentTimeMillis();

            try {
                latch.await();
                repaint();
            } catch (InterruptedException ignored) {
            }

            long elapsed = System.currentTimeMillis() - startTime;
            System.out.println("elapsed time: " + elapsed + " ms");
        }).start();
    }
}
