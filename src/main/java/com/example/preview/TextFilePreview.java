package com.example.preview;

import com.example.filelist.FileListEntry;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;

/**
 * @author innokenty
 */
class TextFilePreview extends FilePreview {

    private static final int MIN_SIZE = 300;

    private static final int PREF_SIZE = 500;

    public TextFilePreview(FileListEntry file) throws Exception {
        super(file.getName());

        //TODO extract into properties
        setMinimumSize(new Dimension(MIN_SIZE, MIN_SIZE));
        setPreferredSize(new Dimension(PREF_SIZE, PREF_SIZE));

        JTextPane textPane = new JTextPane();
        InputStream stream = file.getInputStream();
        textPane.read(stream, null);
        stream.close();

        //TODO support edited files saving
        textPane.setEditable(false);

        add(new JScrollPane(textPane));
    }
}
