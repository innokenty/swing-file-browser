package com.example.filelist;

import java.io.InputStream;

/**
 * @author innokenty
 */
class GoUpFileListEntry implements FileListEntry {

    public GoUpFileListEntry() {
    }

    @Override
    public boolean isDirectory() {
        return true;
    }

    @Override
    public String getName() {
        return "../";
    }

    @Override
    public InputStream getInputStream() throws Exception {
        throw new UnsupportedOperationException("GoUpFileListEntry.getInputStream " +
                "is not supported! You should call the isDirectory() or check the " +
                "instance of the file entry before invocation of this method."
        );
    }
}
