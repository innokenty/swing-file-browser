package com.example;

import com.example.filelist.FileList;

/**
 * @author innokenty
 */
public interface FileListContainer {

    FileList getFileList();

    void onFileListChanged(FileListWatcher watcher);

    void fireListChange();
}
