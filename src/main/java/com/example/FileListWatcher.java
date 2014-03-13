package com.example;

import com.example.filelist.FileList;
import org.jetbrains.annotations.Nullable;

/**
 * @author innokenty
 */
public interface FileListWatcher {

    void onFileListChanged(@Nullable FileList newFileList);
}
