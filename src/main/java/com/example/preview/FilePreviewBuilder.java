package com.example.preview;

import com.example.filelist.FileListEntry;

import java.io.IOException;

/**
 * @author innokenty
 */
interface FilePreviewBuilder {

    boolean supportsMimetype(String mimetype);

    FilePreview getPreviewFor(FileListEntry file) throws IOException;
}
