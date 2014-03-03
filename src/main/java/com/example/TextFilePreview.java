package com.example;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static java.util.Arrays.asList;

/**
 * @author innokenty
 */
public class TextFilePreview extends FilePreview {

    protected static final java.util.List<String> SUPPORTED_EXTENSIONS = asList(
            "", "txt", "log", "xml", "html", "java", "properties"
    );

    public TextFilePreview(File file) throws IOException {
        super(file.getName());

        //TODO extract into properties
        setMinimumSize(new Dimension(300, 300));
        setSize(500, 500);

        JTextPane textPane = new JTextPane();
        textPane.setPage(file.toURI().toURL());

        //TODO support edited files saving
        textPane.setEditable(false);

        add(new JScrollPane(textPane));
    }
}
