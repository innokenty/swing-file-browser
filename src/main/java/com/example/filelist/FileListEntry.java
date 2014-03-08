package com.example.filelist;

import java.io.InputStream;

/**
 * @author innokenty
 */
public interface FileListEntry {

    boolean isHidden();

    boolean isDirectory();

    String getName();

    Iterable<? extends FileListEntry> listFiles();

    InputStream getInputStream();
}
