package com.example.filelist;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.InputStream;

/**
 * @author innokenty
 */
class FtpFileListEntry implements FileListEntry {

    private final FTPClient client;

    private final boolean isDirectory;

    private final String name;

    public FtpFileListEntry(FTPClient client, FTPFile delegate) {
        this.client = client;
        isDirectory = delegate.isDirectory();
        name = delegate.getName();
    }

    @Override
    public boolean isDirectory() {
        return isDirectory;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public InputStream getInputStream() throws Exception {
        if (isDirectory()) {
            throw new UnsupportedOperationException("Calling " +
                    "FileListEntry.getInputStream on a directory not supported! " +
                    "You should call the isDirectory() before invocation of this method."
            );
        }

        InputStream stream = client.retrieveFileStream(getName());
        if (!client.completePendingCommand()) {
            throw new FtpClientException("File transfer failed!");
        }
        return stream;
    }
}
