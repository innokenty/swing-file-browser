package com.example.filelist;

import com.example.Defaults;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

/**
 * @author innokenty
 */
public class FtpFileList extends FileList<FtpFileListEntry> {

    public FtpFileList(
            String hostname, String username, String password)
            throws Exception {
        this(buildSimpleClient(hostname, username, password));
    }

    public FtpFileList(FTPClient client) throws Exception {
        super(new FtpFileListModel(client));
    }

    private static FTPClient buildSimpleClient(
            String hostname, String username, String password)
            throws Exception {

        FTPClient client = new FTPClient();
        client.setDataTimeout(Defaults.FTP_DATA_TIMEOUT);
        client.setControlKeepAliveTimeout(Defaults.FTP_CONTROL_IDLE);
        client.connect(hostname);
        client.setSoTimeout(Defaults.FTP_SO_TIMEOUT);
        int reply = client.getReplyCode();

        if (!FTPReply.isPositiveCompletion(reply)) {
            throw new FtpClientException("Unable to connect! Sorry... " +
                    "Response code is " + reply + ", that's all I know");
        }

        if (!client.login(username, password)) {
            throw new FtpClientException("Unable to login to the ftp server");
        }

        client.setKeepAlive(true);
        return client;
    }
}
