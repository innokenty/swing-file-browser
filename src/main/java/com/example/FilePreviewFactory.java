package com.example;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author innokenty
 */
public class FilePreviewFactory {

    private final Map<String, FilePreviewBuilder> builders = new HashMap<>();

    {
        TextFilePreviewBuilder textFilePreviewBuilder = new TextFilePreviewBuilder();
        builders.put(null, textFilePreviewBuilder);
        builders.put("txt", textFilePreviewBuilder);
    }

    public FilePreview getPreviewDialogFor(File file) throws IOException {
        String extension = FilenameUtils.getExtension(file.getName());
        FilePreviewBuilder builder = builders.get(extension);
        return builder != null ? builder.getPreviewFor(file) : null;
    }
}
