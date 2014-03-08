package com.example.filelist;

import ch.lambdaj.function.convert.Converter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static ch.lambdaj.collection.LambdaCollections.with;

/**
 * @author innokenty
 */
class LocalFileListEntry implements FileListEntry {

    private static final Converter<File, LocalFileListEntry> CONVERTER
            = new Converter<File, LocalFileListEntry>() {
        @Override
        public LocalFileListEntry convert(File from) {
            return new LocalFileListEntry(from);
        }
    };

    private final File delegate;

    public LocalFileListEntry(File delegate) {
        this.delegate = delegate;

    }

    @Override
    public boolean isHidden() {
        return delegate.isHidden();
    }

    @Override
    public boolean isDirectory() {
        return delegate.isDirectory();
    }

    @Override
    public String getName() {
        return delegate.getName();
    }

    @Override
    public Iterable<LocalFileListEntry> listFiles() {
        return with(delegate.listFiles()).convert(CONVERTER);
    }

    @Override
    public InputStream getInputStream() {
        try {
            return new FileInputStream(delegate);
        } catch (FileNotFoundException e) {
            //TODO handle
            throw new RuntimeException(e);
        }
    }

    public LocalFileListEntry getParentFile() {
        File parent = delegate.getParentFile();
        return parent == null ? null : new LocalFileListEntry(parent);
    }
}
