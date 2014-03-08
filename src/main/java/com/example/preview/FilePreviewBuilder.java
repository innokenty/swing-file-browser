package com.example.preview;

import com.example.filelist.FileListEntry;

import java.io.IOException;

/**
 * @author innokenty
 */
interface FilePreviewBuilder {

    boolean supportsExtension(String extension);

    FilePreview getPreviewFor(FileListEntry file) throws IOException;
}
