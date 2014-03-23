package com.example.utils;

import java.awt.*;

/**
 * @author innokenty
 */
public class WindowPositioningUtils {

    public static void bringWindowToScreen(Window window) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        Dimension size = window.getSize();
        int windowWidth = (int) size.getWidth();
        int windowHeight = (int) size.getHeight();

        Point locationOnScreen = window.getLocationOnScreen();
        int left = (int) locationOnScreen.getX();
        int top = (int) locationOnScreen.getY();

        if (screenWidth < left + windowWidth) {
            left = screenWidth - windowWidth;
        }

        if (screenHeight < top + windowHeight) {
            top = screenHeight - windowHeight;
        }

        window.setLocation(left, top);
    }
}
