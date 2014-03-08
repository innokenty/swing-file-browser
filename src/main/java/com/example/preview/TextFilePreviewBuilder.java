package com.example.preview;

import com.example.filelist.FileListEntry;

import java.io.IOException;

/**
 * @author innokenty
 */
class TextFilePreviewBuilder implements FilePreviewBuilder {

    @Override
    public boolean supportsExtension(String extension) {
        return TextFilePreview.SUPPORTED_EXTENSIONS.contains(extension);
    }

    @Override
    public FilePreview getPreviewFor(FileListEntry file) throws IOException {
        return new TextFilePreview(file.getName(), file.getInputStream());
    }
}
