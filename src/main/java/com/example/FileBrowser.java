package com.example;

import javax.swing.*;
import java.awt.*;

import static com.example.Defaults.*;

/**
 * @author innokenty
 */
public class FileBrowser extends JFrame {

    public FileBrowser() throws HeadlessException {
        System.setProperty("apple.laf.useScreenMenuBar", "true");

        setTitle(APP_TITLE);
        setSize(new Dimension(APP_WIDTH, APP_HEIGHT));
        setMinimumSize(new Dimension(APP_MIN_WIDTH, APP_MIN_HEIGHT));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        FileList fileList = new FileList();
        add(new JScrollPane(fileList), BorderLayout.CENTER);
        setJMenuBar(new FileBrowserMenuBar(fileList));
        add(new FileBrowserToolbar(fileList), BorderLayout.NORTH);
    }

    public static void main(String[] args) {
        new FileBrowser().setVisible(true);
    }
}
