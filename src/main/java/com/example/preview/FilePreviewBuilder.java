package com.example.preview;

import com.example.filelist.FileListEntry;

/**
 * @author innokenty
 */
interface FilePreviewBuilder {

    boolean supportsMimetype(String mimetype);

    FilePreview getPreviewFor(FileListEntry file) throws Exception;
}
