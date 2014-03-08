package com.example.preview;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

import static java.util.Arrays.asList;

/**
 * @author innokenty
 */
class TextFilePreview extends FilePreview {

    protected static final java.util.List<String> SUPPORTED_EXTENSIONS = asList(
            "", "txt", "log", "xml", "html", "java", "properties"
    );

    public TextFilePreview(String fileName, InputStream inputStream) throws IOException {
        super(fileName);

        //TODO extract into properties
        setMinimumSize(new Dimension(300, 300));
        setSize(500, 500);

        JTextPane textPane = new JTextPane();
        textPane.read(inputStream, null);

        //TODO support edited files saving
        textPane.setEditable(false);

        add(new JScrollPane(textPane));
    }
}
