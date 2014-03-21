package com.example.filelist;

import com.example.Defaults;
import com.example.utils.SpringUtilities;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;

import javax.swing.*;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;

/**
 * @author innokenty
 */
class NewFtpFileListPanel extends FileListFactory<FtpFileList> {

    private static class FileTypeOptions extends LinkedHashMap<String, Integer> {

        {
            put("ASCII FILE TYPE", FTP.ASCII_FILE_TYPE);
            put("BINARY FILE TYPE", FTP.BINARY_FILE_TYPE);
            put("EBCDIC FILE TYPE", FTP.EBCDIC_FILE_TYPE);
            put("LOCAL FILE TYPE", FTP.LOCAL_FILE_TYPE);
        }

        String[] getKeysAsArray() {
            return this.keySet().toArray(new String[this.size()]);
        }
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private static final FileTypeOptions FILE_TYPE_OPTIONS = new FileTypeOptions();

    private final JTextField hostname;

    private final JTextField port;

    private final JCheckBox ftps;

    private final JTextField username;

    private final JPasswordField password;

    private final JComboBox<String> fileType;

    public NewFtpFileListPanel() {
        setName("FTP");
        setLayout(new SpringLayout());

        JLabel hostnameLabel = new JLabel("hostname", JLabel.TRAILING);
        add(hostnameLabel);
        hostname = new JTextField();
        hostnameLabel.setLabelFor(hostname);
        add(hostname);

        JLabel portLabel = new JLabel("port", JLabel.TRAILING);
        add(portLabel);
        DecimalFormat format = new DecimalFormat("##00");
        format.setMinimumIntegerDigits(1);
        format.setMaximumIntegerDigits(4);
        port = new JFormattedTextField(format);
        port.setText(String.valueOf(FTP.DEFAULT_PORT));
        portLabel.setLabelFor(port);
        add(port);

        JLabel ftpsLabel = new JLabel("ftps", JLabel.TRAILING);
        add(ftpsLabel);
        ftps = new JCheckBox();
        ftpsLabel.setLabelFor(ftps);
        add(ftps);

        JLabel usernameLabel = new JLabel("username", JLabel.TRAILING);
        add(usernameLabel);
        username = new JTextField();
        usernameLabel.setLabelFor(username);
        add(username);

        JLabel passwordLabel = new JLabel("password", JLabel.TRAILING);
        add(passwordLabel);
        password = new JPasswordField();
        passwordLabel.setLabelFor(password);
        add(password);

        JLabel fileTypeLabel = new JLabel("fileType", JLabel.TRAILING);
        add(fileTypeLabel);
        fileType = new JComboBox<>(FILE_TYPE_OPTIONS.getKeysAsArray());
        fileTypeLabel.setLabelFor(fileType);
        add(fileType);

        SpringUtilities.makeCompactGrid(this,
                6, 2,
                6, 6,
                6, 6);
    }

    @Override
    public FtpFileList buildFileList() throws Exception {
        //noinspection SuspiciousMethodCalls
        FTPClient client = buildSimpleClient(
                hostname.getText(),
                getPort(),
                ftps.isSelected(),
                username.getText(),
                new String(password.getPassword()),
                FILE_TYPE_OPTIONS.get(fileType.getSelectedItem())
        );
        return new FtpFileList(client);
    }

    private int getPort() {
        int port;
        try {
            port = Integer.parseInt(this.port.getText());
        } catch (NumberFormatException e) {
            port = FTP.DEFAULT_PORT;
        }
        return port;
    }

    private static FTPClient buildSimpleClient(
            String hostname, int port, boolean useFTPS, String username, String password, int fileType)
            throws Exception {

        FTPClient client = useFTPS ? new FTPSClient() : new FTPClient();
        client.setDataTimeout(Defaults.FTP_DATA_TIMEOUT);
        client.setControlKeepAliveTimeout(Defaults.FTP_CONTROL_IDLE);
        client.connect(hostname, port);
        client.enterLocalPassiveMode();
        client.setSoTimeout(Defaults.FTP_SO_TIMEOUT);
        int reply = client.getReplyCode();

        if (!FTPReply.isPositiveCompletion(reply)) {
            throw new FtpClientException("Unable to connect! Sorry... " +
                    "Response code is " + reply + ", that's all I know");
        }

        if (!client.login(username, password)) {
            throw new FtpClientException("Unable to login to the ftp server");
        }
        client.setFileType(fileType);

        client.setKeepAlive(true);
        return client;
    }
}
