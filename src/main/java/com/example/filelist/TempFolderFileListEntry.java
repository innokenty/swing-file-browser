package com.example.filelist;

import ch.lambdaj.function.convert.Converter;

import java.io.File;

/**
 * @author innokenty
 */
class TempFolderFileListEntry extends LocalFileListEntry {

    private final String fakeName;

    public TempFolderFileListEntry(File delegate, String fakeName) {
        super(delegate);
        this.fakeName = fakeName;
    }

    @Override
    public String getName() {
        return fakeName;
    }

    @Override
    public LocalFileListEntry getParentFile() {
        throw new UnsupportedOperationException(
                "method getParentFile() is not available in TempFolderFileListEntry"
        );
    }

    @Override
    protected Converter<File, LocalFileListEntry> getConverter() {
        return BackwardsLinkedLocalEntry.getConverter(this);
    }
}
