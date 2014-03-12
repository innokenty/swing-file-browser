package com.example.filelist;

import java.io.File;

/**
 * @author innokenty
 */
class LocalFileList extends FileList {

    public LocalFileList() throws Exception {
        this(getDefaultStartingFolder());
    }

    public LocalFileList(File startingFolder) throws Exception {
        super(new LocalFileListModel(startingFolder));
    }

    private static File getDefaultStartingFolder() {
        String homeDirPath = System.getProperty("user.home");
        //noinspection ConstantConditions
        return homeDirPath != null || new File(homeDirPath).isDirectory()
                ? new File(homeDirPath)
                : new File(System.getProperty("user.dir"));
    }
}
