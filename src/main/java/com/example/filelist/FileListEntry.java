package com.example.filelist;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author innokenty
 */
public interface FileListEntry {

    boolean isDirectory();

    String getName();

    InputStream getInputStream() throws IOException;
}
