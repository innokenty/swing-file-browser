package com.example;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @author innokenty
 */
public class TextFilePreview extends FilePreview {

    public TextFilePreview(File file) throws IOException {
        super(file.getName());
        setMinimumSize(new Dimension(300, 300));
        setSize(500, 500);

        JTextPane textPane = new JTextPane();
        textPane.setPage(file.toURI().toURL());

        //TODO support edited files saving
        textPane.setEditable(false);

        add(textPane);
    }
}
