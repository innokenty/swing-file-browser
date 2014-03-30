package com.example.filelist;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * @author innokenty
 */
abstract class FileListModel<T extends FileListEntry>
        extends DefaultListModel<FileListEntry> {

    public final boolean openFolder(FileListEntry folder) throws Exception {
        if (folder instanceof GoUpFileListEntry) {
            goUp();
            return true;
        } else if (!folder.isDirectory()) {
            return false;
        } else {
            try {
                //noinspection unchecked
                if (openFolderImpl((T) folder)) {
                    repaint();
                    return true;
                }
            } catch (ClassCastException e) {
                throw new FileListException(
                        "Trying to open folder of invalid type for this list!", e
                );
            }
        }
        return false;
    }

    public void addListDataListeners(ListDataListener[] listeners) {
        List<ListDataListener> thatListeners = new ArrayList<>(asList(listeners));
        thatListeners.removeAll(asList(getListDataListeners()));
        for (ListDataListener listener : thatListeners) {
            addListDataListener(listener);
        }
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
        if (canGoUp()) {
            addElement(new GoUpFileListEntry());
        }
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
