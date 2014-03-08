package com.example;

import com.example.filelist.FileList;

/**
 * @author innokenty
 */
public interface FileListWatcher {

    void onFileListChanged(FileList newFileList);
}
