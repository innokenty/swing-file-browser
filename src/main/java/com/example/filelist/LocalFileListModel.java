package com.example.filelist;

import com.example.Defaults;

import javax.swing.*;
import java.io.File;

/**
 * @author innokenty
 */
class LocalFileListModel
        extends DefaultListModel<LocalFileListEntry>
        implements FileListModel<LocalFileListEntry> {

    private boolean showHiddenFiles = Defaults.SHOW_HIDDEN_FILES;

    private LocalFileListEntry currentFolder;

    public LocalFileListModel(File startingFolder) {
        this(new LocalFileListEntry(startingFolder));
    }

    public LocalFileListModel(LocalFileListEntry currentFolder) {
        this.currentFolder = currentFolder;
        repaint();
    }

    @Override
    public void openFolder(LocalFileListEntry folder) {
        if (folder.isDirectory()) {
            this.currentFolder = folder;
            repaint();
        } else {
            throw new IllegalArgumentException("folder parameter of type " +
                    "java.io.File must represent an actual folder!");
        }
    }

    @Override
    public boolean canGoUp() {
        return currentFolder.getParentFile() != null;
    }

    @Override
    public boolean goUp() {
        if (!canGoUp()) {
            return false;
        }
        openFolder(currentFolder.getParentFile());
        return true;
    }

    @Override
    public boolean isShowingHiddenFiles() {
        return showHiddenFiles;
    }

    @Override
    public void setShowHiddenFiles(boolean showHiddenFiles) {
        if (this.showHiddenFiles != showHiddenFiles) {
            this.showHiddenFiles = showHiddenFiles;
            repaint();
        } else {
            this.showHiddenFiles = showHiddenFiles;
        }
    }

    private void repaint() {
        super.clear();
        for (LocalFileListEntry entry : listFiles()) {
            super.addElement(entry);
        }
        super.fireContentsChanged(this, 0, size());
    }

    private Iterable<LocalFileListEntry> listFiles() {
        return currentFolder.listFiles(showHiddenFiles);
    }
}
