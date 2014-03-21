package com.example.filelist;

import org.apache.commons.net.ftp.FTPClient;

/**
 * @author innokenty
 */
class FtpFileList extends FileList {

    public FtpFileList(FTPClient client) throws Exception {
        super(new FtpFileListModel(client));
    }
}
