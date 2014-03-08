package com.example;

import com.example.filelist.LocalFileList;

import javax.swing.*;
import java.awt.*;

import static com.example.Defaults.*;

/**
 * @author innokenty
 */
public class FileBrowser extends JFrame {

    private LocalFileList fileList;

    static {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
    }

    public FileBrowser() throws HeadlessException {
        initWindowProperties();
        initComponents();
    }

    private void initWindowProperties() {
        setTitle(APP_TITLE);
        setSize(new Dimension(APP_WIDTH, APP_HEIGHT));
        setMinimumSize(new Dimension(APP_MIN_WIDTH, APP_MIN_HEIGHT));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        fileList = new LocalFileList();
        add(new JScrollPane(fileList), BorderLayout.CENTER);
        setJMenuBar(new FileBrowserMenuBar(fileList));
        add(new FileBrowserToolbar(fileList), BorderLayout.NORTH);
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        fileList.requestFocusInWindow();
    }

    public static void main(String[] args) {
        new FileBrowser().setVisible(true);
    }
}
