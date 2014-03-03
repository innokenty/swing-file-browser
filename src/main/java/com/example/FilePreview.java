package com.example;

import javax.swing.*;

/**
 * @author innokenty
 */
public abstract class FilePreview extends JDialog {

    public FilePreview(String fileName) {
        setTitle(fileName);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
