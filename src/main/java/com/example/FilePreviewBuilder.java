package com.example;

import java.io.File;
import java.io.IOException;

/**
 * @author innokenty
 */
public interface FilePreviewBuilder {

    FilePreview getPreviewFor(File file) throws IOException;
}
