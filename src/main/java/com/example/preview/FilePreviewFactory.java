package com.example.preview;

import com.example.filelist.FileListEntry;

import javax.activation.MimetypesFileTypeMap;
import java.awt.*;
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

    public static FilePreview getPreviewDialogFor(FileListEntry file, Component owner)
            throws Exception {
        String nameLC = file.getName().toLowerCase();
        String mimetype = new MimetypesFileTypeMap().getContentType(nameLC);
        FilePreviewBuilder builder = selectFirst(BUILDERS,
                having(on(FilePreviewBuilder.class).supportsMimetype(mimetype)));
        if (builder != null) {
            FilePreview preview = builder.getPreviewFor(file);
            preview.pack();
            preview.setLocationRelativeTo(owner);
            return preview;
        } else {
            return null;
        }
    }
}
