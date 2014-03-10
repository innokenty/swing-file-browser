package com.example.preview;

import com.example.filelist.FileListEntry;

import java.io.IOException;

/**
 * @author innokenty
 */
public class ImageFilePreviewBuilder implements FilePreviewBuilder {

    @Override
    public boolean supportsMimetype(String mimetype) {
        return mimetype.startsWith("image/");
    }

    @Override
    public FilePreview getPreviewFor(FileListEntry file) throws IOException {
        return new ImageFilePreview(file);
    }
}
