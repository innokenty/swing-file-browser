package com.example.filelist;

import ch.lambdaj.function.convert.Converter;
import com.example.Defaults;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.IOException;

import static ch.lambdaj.collection.LambdaCollections.with;

/**
 * @author innokenty
 */
class FtpFileListModel extends FileListModel<FtpFileListEntry> {

    private final FTPClient client;

    private String currentFolderName = "/";

    public FtpFileListModel(FTPClient client) throws Exception {
        this.client = client;
        client.setListHiddenFiles(Defaults.SHOW_HIDDEN_FILES);
        repaint();
    }

    @Override
    public void openFolderImpl(FtpFileListEntry folder) throws FtpClientException {
        try {
            String folderName = folder.getName();
            client.changeWorkingDirectory(folderName);
            currentFolderName = folderName;
        } catch (IOException e) {
            throw new FtpClientException("Unable to open folder!", e);
        }
    }

    @Override
    public boolean canGoUp() {
        return true;
    }

    @Override
    public boolean goUpImpl() throws Exception {
        boolean result = client.changeToParentDirectory();
        if (result) {
            repaint();
        }
        return result;
    }

    @Override
    public boolean isShowingHiddenFiles() {
        return client.getListHiddenFiles();
    }

    @Override
    public void setShowHiddenFilesImpl(boolean showHiddenFiles) {
        client.setListHiddenFiles(showHiddenFiles);
    }

    @Override
    protected Iterable<FtpFileListEntry> listFiles() throws IOException {
        return with(client.listFiles())
                .convert(new Converter<FTPFile, FtpFileListEntry>() {
                    @Override
                    public FtpFileListEntry convert(FTPFile file) {
                        return new FtpFileListEntry(client, file);
                    }
                });
    }

    @Override
    public String getCurrentFolderName() {
        return currentFolderName;
    }
}
