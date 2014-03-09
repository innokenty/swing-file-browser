package com.example.filelist;

import ch.lambdaj.collection.LambdaList;
import ch.lambdaj.function.convert.Converter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static ch.lambdaj.Lambda.having;
import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.collection.LambdaCollections.with;
import static org.hamcrest.Matchers.equalTo;

/**
 * @author innokenty
 */
class LocalFileListEntry implements FileListEntry {

    private final File delegate;

    public LocalFileListEntry(File delegate) {
        this.delegate = delegate;
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
    public InputStream getInputStream() throws FileNotFoundException {
        return new FileInputStream(delegate);
    }

    public LocalFileListEntry getParentFile() {
        File parent = delegate.getParentFile();
        return parent == null ? null : new LocalFileListEntry(parent);
    }

    public Iterable<LocalFileListEntry> listFiles(final boolean showHidden) {
        LambdaList<File> files = with(delegate.listFiles());
        if (!showHidden) {
            files = files.remove(
                    having(on(File.class).isHidden(), equalTo(true))
            );
        }
        return files.convert(new Converter<File, LocalFileListEntry>() {
            @Override
            public LocalFileListEntry convert(File file) {
                return new LocalFileListEntry(file);
            }
        });
    }
}
