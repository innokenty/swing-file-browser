package com.example;

import javax.swing.*;
import java.io.File;
import java.io.FileFilter;

/**
 * @author innokenty
 */
class FileListModel extends DefaultListModel<File> {

    private boolean showHiddenFiles = Defaults.SHOW_HIDDEN_FILES;

    private File currentFolder;

    public FileListModel(File currentFolder) {
        openFolder(currentFolder);
    }

    public void openFolder(File folder) {
        if (folder.isDirectory()) {
            this.currentFolder = folder;
            repaint();
        } else {
            throw new IllegalArgumentException("folder parameter of type " +
                    "java.io.File must represent an actual folder!");
        }
    }

    public boolean canGoUp() {
        return currentFolder.getParentFile() != null;
    }

    public void goUp() {
        if (!canGoUp()) {
            throw new IllegalStateException("current folder is top-level " +
                    "and does not have any parent folder!");
        }
        openFolder(currentFolder.getParentFile());
    }

    public void setShowHiddenFiles(boolean showHiddenFiles) {
        if (this.showHiddenFiles != showHiddenFiles) {
            this.showHiddenFiles = showHiddenFiles;
            repaint();
        } else {
            this.showHiddenFiles = showHiddenFiles;
        }
    }

    public void toggleShowHiddenFiles() {
        setShowHiddenFiles(!showHiddenFiles);
    }

    private void repaint() {
        super.clear();
        for (File file : listFiles()) {
            super.addElement(file);
        }
        super.fireContentsChanged(this, 0, size());
    }

    private File[] listFiles() {
        return this.currentFolder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return showHiddenFiles || !file.isHidden();
            }
        });
    }
}
