package com.example;

import com.example.filelist.FileList;
import org.jetbrains.annotations.Nullable;

/**
 * @author innokenty
 */
public interface FileListSwitchListener {

    void onFileListSwitched(@Nullable FileList newFileList);
}
