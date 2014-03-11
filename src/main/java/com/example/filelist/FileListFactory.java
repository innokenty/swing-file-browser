package com.example.filelist;

/**
 * @author innokenty
 */
interface FileListFactory<T extends FileList> {

    T buildFileList() throws Exception;
}
