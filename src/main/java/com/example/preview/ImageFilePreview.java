package com.example.preview;

import com.example.filelist.FileListEntry;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

/**
 * @author innokenty
 */
public class ImageFilePreview extends FilePreview {

    private static final int MIN_SIZE = 300;

    private static final int PREF_SIZE = 800;

    private static final int HORIZONTAL_DELTA = 30;

    private static final int VERTICAL_DELTA = 70;

    public ImageFilePreview(FileListEntry file) throws Exception {
        super(file.getName());

        InputStream stream = file.getInputStream();
        BufferedImage image = ImageIO.read(stream);
        stream.close();

        setPreferredSize(new Dimension(PREF_SIZE, PREF_SIZE));
        setMinimumSize(new Dimension(
                Math.min(image.getWidth() + HORIZONTAL_DELTA, MIN_SIZE),
                Math.min(image.getHeight() + VERTICAL_DELTA, MIN_SIZE)
        ));

        JLabel label = new JLabel(new ImageIcon(image));
        add((new JScrollPane(label)));
    }
}
