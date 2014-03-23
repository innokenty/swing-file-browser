package com.example.preview;

import com.example.filelist.FileListEntry;

/**
 * @author innokenty
 */
class TextFilePreviewBuilder implements FilePreviewBuilder {

    @Override
    public boolean supportsMimetype(String mimetype) {
        return mimetype.startsWith("text/");
    }

    @Override
    public FilePreview getPreviewFor(FileListEntry file) {
        return new TextFilePreview(file);
    }
}
