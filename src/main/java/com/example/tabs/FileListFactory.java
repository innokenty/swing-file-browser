package com.example.tabs;

import com.example.filelist.FileList;

/**
 * @author innokenty
 */
interface FileListFactory<T extends FileList> {

    T buildFileList() throws Exception;
}
