package com.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * @author innokenty
 */
public enum Icon {
    OOPS("img/oops.png"),
    SORRY_BRO("img/sorry-bro.jpg"),
    UP("img/up.png"),
    GHOST("img/ghost.png"),
    PLUS("img/plus.png"),
    ZOOM_IN("img/zoom_in.png"),
    ZOOM_OUT("img/zoom_out.png"),
    ZOOM_EXPAND("img/zoom_expand.png"),
    ZOOM_SHRINK("img/zoom_shrink.png"),;

    private static final int SCALE_METHOD = Image.SCALE_SMOOTH;

    private final String resourceName;

    Icon(String resourceName) {
        this.resourceName = resourceName;
    }

    private URL getResource() {
        return this.getClass().getClassLoader().getResource(resourceName);
    }

    public ImageIcon build() {
        return new ImageIcon(getResource());
    }

    public ImageIcon build(int width, int height) {
        URL resource = getResource();
        try {
            BufferedImage image = ImageIO.read(resource);
            Image scaled = image.getScaledInstance(width, height, SCALE_METHOD);
            return new ImageIcon(scaled);
        } catch (IOException e) {
            e.printStackTrace();
            return build();
        }
    }
}
