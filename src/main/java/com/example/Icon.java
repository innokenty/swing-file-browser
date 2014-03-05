package com.example;

import javax.swing.*;

/**
 * @author innokenty
 */
enum Icon {
    OOPS("img/oops.png"),
    SORRY_BRO("img/sorry-bro.jpg");

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
