package com.example.preview;

import com.example.filelist.FileListEntry;

/**
 * @author innokenty
 */
class ImageFilePreviewBuilder implements FilePreviewBuilder {

    @Override
    public boolean supportsMimetype(String mimetype) {
        return mimetype.startsWith("image/");
    }

    @Override
    public FilePreview getPreviewFor(FileListEntry file) {
        return new ImageFilePreview(file);
    }
}
