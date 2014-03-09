package com.example;

import com.example.components.FileBrowserMenuBar;
import com.example.components.GoUpButton;
import com.example.components.ShowHiddenFilesButton;
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

    public FileBrowser() throws Exception {
        initWindowProperties();
        initFileList();
        initMenuBar();
        initToolbar();
        fireListChange();
    }

    private void initWindowProperties() {
        setTitle(APP_TITLE);
        setSize(new Dimension(APP_WIDTH, APP_HEIGHT));
        setMinimumSize(new Dimension(APP_MIN_WIDTH, APP_MIN_HEIGHT));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initFileList() throws Exception {
        fileList = new LocalFileList();
        add(new JScrollPane(fileList), BorderLayout.CENTER);
    }

    private void initMenuBar() {
        setJMenuBar(new FileBrowserMenuBar(this));
    }

    private void initToolbar() {
        JToolBar toolBar = new JToolBar();
        toolBar.add(new GoUpButton(this));
        toolBar.add(new ShowHiddenFilesButton(this));
        add(toolBar, BorderLayout.NORTH);
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

    public static void main(String[] args) throws Exception {
        new FileBrowser().setVisible(true);
    }
}
