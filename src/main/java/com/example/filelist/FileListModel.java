package com.example.filelist;

import javax.swing.event.ListDataListener;
import java.io.File;

/**
 * @author innokenty
 */
public interface FileListModel {

    void openFolder(File folder);

    boolean canGoUp();

    void goUp();

    void setShowHiddenFiles(boolean showHiddenFiles);

    void toggleShowHiddenFiles();

    void addListDataListener(ListDataListener l);
}
