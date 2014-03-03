package com.example;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static ch.lambdaj.Lambda.*;
import static java.util.Arrays.asList;
import static org.apache.commons.io.FilenameUtils.getExtension;

/**
 * @author innokenty
 */
public class FilePreviewFactory {

    private static final List<? extends FilePreviewBuilder> BUILDERS = asList(
            new TextFilePreviewBuilder()
    );

    public FilePreview getPreviewDialogFor(File file) throws IOException {
        FilePreviewBuilder builder = selectFirst(
                BUILDERS,
                having(on(FilePreviewBuilder.class)
                        .supportsExtension(getExtension(file.getName())))
        );
        return builder != null ? builder.getPreviewFor(file) : null;
    }
}
