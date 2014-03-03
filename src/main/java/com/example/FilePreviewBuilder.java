package com.example;

import java.io.File;
import java.io.IOException;

/**
 * @author innokenty
 */
public interface FilePreviewBuilder {

    boolean supportsExtension(String extension);

    FilePreview getPreviewFor(File file) throws IOException;
}
