/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pepsoft.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.jetbrains.annotations.NonNls;

/**
 *
 * @author pepijn
 */
@NonNls
public final class IconUtils {
    private IconUtils() {
        // Prevent instantiation
    }

    /**
     * Load an icon from the classpath using the system class loader.
     *
     * @param path The path of the image to load.
     * @return The specified icon, or <code>null</code> if the specified path
     *     did not contain a resource.
     */
    public static ImageIcon loadIcon(String path) {
        BufferedImage image = loadImage(path);
        return (image != null) ? new ImageIcon(image) : null;
    }
    
    /**
     * Load an icon from the classpath using a specific class loader.
     *
     * @param classLoader The class loader to use to load the image.
     * @param path The path of the image to load.
     * @return The specified icon, or <code>null</code> if the specified path
     *     did not contain a resource.
     */
    public static ImageIcon loadIcon(ClassLoader classLoader, String path) {
        BufferedImage image = loadImage(classLoader, path);
        return (image != null) ? new ImageIcon(image) : null;
    }

    /**
     * Load an image from the classpath using the system class loader.
     *
     * @param path The path of the image to load.
     * @return The specified image, or <code>null</code> if the specified path
     *     did not contain a resource.
     */
    public static BufferedImage loadImage(String path) {
        try {
            URL url = ClassLoader.getSystemResource(path);
            if (url != null) {
                return ImageIO.read(url);
            } else {
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException("I/O error loading image " + path, e);
        }
    }
    
    /**
     * Load an image from the classpath using a specific class loader.
     *
     * @param classLoader The class loader to use to load the image.
     * @param path The path of the image to load.
     * @return The specified image, or <code>null</code> if the specified path
     *     did not contain a resource.
     */
    public static BufferedImage loadImage(ClassLoader classLoader, String path) {
        try {
            URL url = classLoader.getResource(path);
            if (url != null) {
                return ImageIO.read(url);
            } else {
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException("I/O error loading image " + path, e);
        }
    }

    /**
     * Create a 16x16 pixel icon of a solid colour.
     *
     * @param colour The colour as a combind rgb value.
     * @return A 16x16 icon of the specified colour.
     */
    public static Icon createColourIcon(int colour) {
        BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                image.setRGB(x, y, colour);
            }
        }
        return new ImageIcon(image);
    }

    /**
     * Scale a square icon using bicubic scaling.
     *
     * @param icon The icon to scale.
     * @param size The size (edge to edge) of the scaled icon.
     * @return The scaled icon.
     */
    public static ImageIcon scaleIcon(ImageIcon icon, int size) {
        return new ImageIcon(scaleIcon(icon.getImage(), size));
    }

    /**
     * Scale a square icon using bicubic scaling.
     *
     * @param iconImage The icon to scale.
     * @param size The size (edge to edge) of the scaled icon.
     * @return The scaled icon.
     */
    public static BufferedImage scaleIcon(Image iconImage, int size) {
        BufferedImage newImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = newImage.createGraphics();
        try {
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2.drawImage(iconImage, 0, 0, size, size, null);
        } finally {
            g2.dispose();
        }
        return newImage;
    }
}