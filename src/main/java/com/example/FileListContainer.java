package com.example;

import com.example.filelist.FileList;

/**
 * @author innokenty
 */
public interface FileListContainer {

    FileList getFileList();

    void addFileListChangeListener(FileListChangeListener watcher);

    void fireListChange();
}
