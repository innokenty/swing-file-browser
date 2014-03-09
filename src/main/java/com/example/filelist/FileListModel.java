package com.example.filelist;

import javax.swing.*;
import javax.swing.event.ListDataListener;

/**
 * @author innokenty
 */
public interface FileListModel<T extends FileListEntry>
    extends ListModel<T> {

    void openFolder(T folder) throws Exception;

    boolean canGoUp();

    boolean goUp() throws Exception;

    boolean isShowingHiddenFiles();

    void setShowHiddenFiles(boolean showHiddenFiles);

    void addListDataListener(ListDataListener l);

    void removeListDataListener(ListDataListener l);
}
