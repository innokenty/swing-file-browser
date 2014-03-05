package com.example.preview;

import java.io.File;
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
    public FilePreview getPreviewFor(File file) throws IOException {
        return new TextFilePreview(file);
    }
}
