package com.example;

import com.example.components.FileBrowserMenuBar;
import com.example.components.GoUpButton;
import com.example.components.ShowHiddenFilesButton;
import com.example.tabs.FileListTabbedPane;

import javax.swing.*;
import java.awt.*;

import static com.example.Defaults.*;

/**
 * @author innokenty
 */
public class FileBrowser extends JFrame {

    static {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
    }

    private final FileListTabbedPane tabbedPane;

    public FileBrowser() throws Exception {
        initWindowProperties();
        tabbedPane = new FileListTabbedPane();
        add(tabbedPane);
        initMenuBar();
        initToolbar();
    }

    private void initWindowProperties() {
        setTitle(APP_TITLE);
        setSize(new Dimension(APP_WIDTH, APP_HEIGHT));
        setMinimumSize(new Dimension(APP_MIN_WIDTH, APP_MIN_HEIGHT));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initMenuBar() {
        setJMenuBar(new FileBrowserMenuBar(tabbedPane));
    }

    private void initToolbar() {
        JToolBar toolBar = new JToolBar();
        toolBar.add(new GoUpButton(tabbedPane));
        toolBar.add(new ShowHiddenFilesButton(tabbedPane));
        add(toolBar, BorderLayout.NORTH);
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
    }

    public static void main(String[] args) throws Exception {
        new FileBrowser().setVisible(true);
    }
}
