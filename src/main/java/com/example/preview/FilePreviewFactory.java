package com.example.preview;

import com.example.filelist.FileListEntry;

import javax.activation.MimetypesFileTypeMap;
import java.io.IOException;
import java.util.List;

import static ch.lambdaj.Lambda.*;
import static java.util.Arrays.asList;

/**
 * @author innokenty
 */
public class FilePreviewFactory {

    private FilePreviewFactory() {
    }

    private static final List<? extends FilePreviewBuilder> BUILDERS = asList(
            new TextFilePreviewBuilder(),
            new ImageFilePreviewBuilder()
    );

    public static FilePreview getPreviewDialogFor(FileListEntry file)
            throws IOException {
        String mimetype = new MimetypesFileTypeMap().getContentType(file.getName());
        FilePreviewBuilder builder = selectFirst(
                BUILDERS,
                having(on(FilePreviewBuilder.class)
                        .supportsMimetype(mimetype))
        );
        return builder != null ? builder.getPreviewFor(file) : null;
    }
}
