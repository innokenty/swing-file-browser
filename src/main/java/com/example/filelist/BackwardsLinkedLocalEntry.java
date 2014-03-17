package com.example.filelist;

import ch.lambdaj.function.convert.Converter;

import java.io.File;

/**
 * @author innokenty
 */
class BackwardsLinkedLocalEntry extends LocalFileListEntry {

    private final LocalFileListEntry parent;

    public BackwardsLinkedLocalEntry(File delegate, LocalFileListEntry parent) {
        super(delegate);
        this.parent = parent;
    }

    @Override
    public LocalFileListEntry getParentFile() {
        return parent;
    }

    @Override
    protected Converter<File, LocalFileListEntry> getConverter() {
        return getConverter(this);
    }

    protected static Converter<File, LocalFileListEntry> getConverter(
            final LocalFileListEntry parent) {
        return new Converter<File, LocalFileListEntry>() {
            @Override
            public LocalFileListEntry convert(File from) {
                return new BackwardsLinkedLocalEntry(
                        from, parent
                );
            }
        };
    }
}
