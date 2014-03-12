package com.example.filelist;

import javax.swing.*;

/**
 * @author innokenty
 */
public abstract class FileListModel<T extends FileListEntry>
        extends DefaultListModel<T> {

    public final boolean openFolder(T folder) throws Exception {
        if (!folder.isDirectory()) {
            return false;
        }
        if (openFolderImpl(folder)) {
            repaint();
            return true;
        }
        return false;
    }

    public final boolean goUp() throws Exception {
        return canGoUp() && goUpImpl();
    }

    public final void setShowHiddenFiles(boolean showHiddenFiles) throws Exception {
        if (this.isShowingHiddenFiles() != showHiddenFiles) {
            setShowHiddenFilesImpl(showHiddenFiles);
            repaint();
        } else {
            setShowHiddenFilesImpl(showHiddenFiles);
        }
    }

    protected final void repaint() throws Exception {
        clear();
        for (T entry : listFiles()) {
            addElement(entry);
        }
        fireContentsChanged();
    }

    public final void fireContentsChanged() {
        fireContentsChanged(this, 0, size());
    }

    public abstract boolean canGoUp();

    public abstract boolean isShowingHiddenFiles();

    public abstract String getCurrentFolderName();


    protected abstract boolean openFolderImpl(T folder) throws Exception;

    protected abstract boolean goUpImpl() throws Exception;

    protected abstract void setShowHiddenFilesImpl(boolean showHiddenFiles);

    protected abstract Iterable<T> listFiles() throws Exception;
}
