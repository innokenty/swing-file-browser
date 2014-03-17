package com.example.filelist;

import javax.swing.*;

/**
 * @author innokenty
 */
abstract class FileListFactory<T extends FileList> extends JPanel {

    public abstract T buildFileList() throws Exception;
}
