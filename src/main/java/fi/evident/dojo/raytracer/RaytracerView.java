/*
 * Copyright (c) 2011 Evident Solutions Oy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package fi.evident.dojo.raytracer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class RaytracerView extends JComponent {

    private final BufferedImage image = new BufferedImage(600, 600, BufferedImage.TYPE_INT_RGB);
    private final Raytracer raytracer;
    
    public RaytracerView(Scene scene) {
        raytracer = new Raytracer(scene, image.getWidth(), image.getHeight());        
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
    
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
            new Thread(new Runnable() {
                public void run() {
                    int width = image.getWidth();
                    int height = image.getHeight();
                    int[] rgbArray = new int[width]; 

                    int y;
                    while ((y = row.incrementAndGet()) < height) {
                        for (int x = 0; x < width; x++)
                            rgbArray[x] = raytracer.colorFor(x, y).toARGB();
                        
                        image.setRGB(0, y, width, 1, rgbArray, 0, 1);
                        
                        if ((y % repaintInterval) == 0)
                            repaint();
                    }
                   
                    latch.countDown();
               }
            }).start();
        
        new Thread(new Runnable() {
            public void run() {
                long startTime = System.currentTimeMillis();
                
                try {
                    latch.await();
                    repaint();
                } catch (InterruptedException e) {
                }

                long elapsed = System.currentTimeMillis() - startTime;
                System.out.println("elapsed time: " + elapsed + " ms");
            }
        }).start();
    }
}
