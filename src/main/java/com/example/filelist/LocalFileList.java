package com.example.filelist;

import javax.swing.filechooser.FileSystemView;
import java.io.File;

/**
 * @author innokenty
 */
class LocalFileList extends FileList {

    public LocalFileList() throws Exception {
        this(FileSystemView.getFileSystemView().getHomeDirectory());
    }

    public LocalFileList(File startingFolder) throws Exception {
        super(new LocalFileListModel(startingFolder));
    }
}
