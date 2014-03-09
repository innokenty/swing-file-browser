package com.example.filelist;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author innokenty
 */
class FtpFileListEntry implements FileListEntry {

    private final FTPClient client;

    private final FTPFile delegate;

    public FtpFileListEntry(FTPClient client, FTPFile delegate) {
        this.client = client;
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
    public InputStream getInputStream() throws IOException {
        return client.retrieveFileStream(delegate.getName());
    }
}
