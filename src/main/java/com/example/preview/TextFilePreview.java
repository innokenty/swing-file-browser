package com.example.preview;

import com.example.filelist.FileListEntry;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static java.util.Arrays.asList;

/**
 * @author innokenty
 */
class TextFilePreview extends FilePreview {

    protected static final java.util.List<String> SUPPORTED_EXTENSIONS = asList(
            "", "txt", "log", "xml", "html", "java", "properties"
    );

    public TextFilePreview(FileListEntry file) throws IOException {
        super(file.getName());

        //TODO extract into properties
        setMinimumSize(new Dimension(300, 300));
        setSize(500, 500);

        JTextPane textPane = new JTextPane();
        textPane.read(file.getInputStream(), null);

        //TODO support edited files saving
        textPane.setEditable(false);

        add(new JScrollPane(textPane));
    }
}
