package com.example.preview;

import java.io.File;
import java.io.IOException;

/**
 * @author innokenty
 */
interface FilePreviewBuilder {

    boolean supportsExtension(String extension);

    FilePreview getPreviewFor(File file) throws IOException;
}
