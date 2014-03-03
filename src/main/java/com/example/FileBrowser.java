package com.example;

import javax.swing.*;
import java.awt.*;

/**
 * @author innokenty
 */
public class FileBrowser extends JFrame {

    public FileBrowser() throws HeadlessException {
        System.setProperty("apple.laf.useScreenMenuBar", "true");

        setTitle("Example File Browser");
        setSize(new Dimension(Defaults.APP_WIDTH, Defaults.APP_HEIGHT));
        setMinimumSize(new Dimension(Defaults.APP_MIN_WIDTH, Defaults.APP_MIN_HEIGHT));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        FileList fileList = new FileList();
        add(new JScrollPane(fileList), BorderLayout.CENTER);
        setJMenuBar(new FileBrowserMenuBar(fileList));
    }

    public static void main(String[] args) {
        new FileBrowser().setVisible(true);
    }
}
