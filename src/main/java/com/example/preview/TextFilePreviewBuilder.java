package com.example.preview;

import com.example.filelist.FileListEntry;

import java.io.IOException;

/**
 * @author innokenty
 */
class TextFilePreviewBuilder implements FilePreviewBuilder {

    @Override
    public boolean supportsMimetype(String mimetype) {
        return mimetype.startsWith("text/");
    }

    @Override
    public FilePreview getPreviewFor(FileListEntry file) throws IOException {
        return new TextFilePreview(file);
    }
}
