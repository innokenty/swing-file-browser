package com.example;

import javax.swing.*;

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
    ZOOM_SHRINK("img/zoom_shrink.png"),
    ;

    private final String resourceName;

    Icon(String resourceName) {
        this.resourceName = resourceName;
    }

    public ImageIcon build() {
        return new ImageIcon(this
                .getClass()
                .getClassLoader()
                .getResource(resourceName));
    }
}
