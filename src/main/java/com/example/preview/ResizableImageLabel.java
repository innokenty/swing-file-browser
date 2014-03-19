package com.example.preview;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static org.apache.commons.lang3.math.NumberUtils.min;

/**
 * @author innokenty
 */
class ResizableImageLabel extends JLabel {

    private static final double ZOOM_BASE = 1.2;

    private static final int SCALE_METHOD = Image.SCALE_DEFAULT;

    private final BufferedImage originalImage;

    private double currentPercentage = 1;

    public ResizableImageLabel(InputStream stream) throws IOException {
        originalImage = ImageIO.read(stream);
        stream.close();

        setHorizontalAlignment(JLabel.CENTER);
        setVerticalAlignment(JLabel.CENTER);
        updateIcon();
    }

    private void updateIcon() {
        setIcon(new ImageIcon(
                originalImage.getScaledInstance(
                        (int) (getImageWidth() * currentPercentage),
                        (int) (getImageHeight() * currentPercentage),
                        SCALE_METHOD
                )
        ));
    }

    public int getImageWidth() {
        return originalImage.getWidth();
    }

    public int getImageHeight() {
        return originalImage.getHeight();
    }

    public void zoomOut() {
        currentPercentage /= ZOOM_BASE;
        updateIcon();
    }

    public void zoomIn() {
        currentPercentage *= ZOOM_BASE;
        updateIcon();
    }

    public void resetZoom() {
        currentPercentage = 1;
        updateIcon();
    }

    public void fitIn(double containerWidth, double containerHeight) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        currentPercentage = min(
                1,
                width == 0 ? 1 : containerWidth / width,
                height == 0 ? 1 : containerHeight / height
        );
        updateIcon();
    }

    public Dimension getSameShapedDimension(int maxBound) {
        double ratio = (double) getImageWidth() / getImageHeight();
        return new Dimension(
                ratio > 1 ? maxBound : (int) (maxBound * ratio),
                ratio > 1 ? (int) (maxBound / ratio) : maxBound
        );
    }
}
