package com.example;

import com.example.components.FileBrowserMenuBar;
import com.example.components.FileBrowserToolbar;
import com.example.filelist.FileList;
import com.example.filelist.LocalFileList;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.Defaults.*;

/**
 * @author innokenty
 */
public class FileBrowser extends JFrame implements FileListContainer {

    static {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
    }

    private LocalFileList fileList;

    private final List<FileListWatcher> watchers = new ArrayList<>();

    public FileBrowser() throws HeadlessException {
        initWindowProperties();
        initFileList();
        initComponents();
        fireListChange();
    }

    private void initWindowProperties() {
        setTitle(APP_TITLE);
        setSize(new Dimension(APP_WIDTH, APP_HEIGHT));
        setMinimumSize(new Dimension(APP_MIN_WIDTH, APP_MIN_HEIGHT));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initFileList() {
        fileList = new LocalFileList();
        add(new JScrollPane(fileList), BorderLayout.CENTER);
    }

    private void initComponents() {
        setJMenuBar(new FileBrowserMenuBar(this));
        add(new FileBrowserToolbar(this), BorderLayout.NORTH);
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        fileList.requestFocusInWindow();
    }

    @Override
    public FileList getFileList() {
        return fileList;
    }

    @Override
    public void onFileListChanged(FileListWatcher watcher) {
        watchers.add(watcher);
    }

    private void fireListChange() {
        for (FileListWatcher watcher : watchers) {
            watcher.onFileListChanged(fileList);
        }
    }

    public static void main(String[] args) {
        new FileBrowser().setVisible(true);
    }
}
