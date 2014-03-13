package com.example;

import com.example.filelist.FileList;
import com.sun.istack.internal.Nullable;

/**
 * @author innokenty
 */
public interface FileListWatcher {

    void onFileListChanged(@Nullable FileList newFileList);
}
