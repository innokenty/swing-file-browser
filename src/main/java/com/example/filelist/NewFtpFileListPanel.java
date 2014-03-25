package com.example.filelist;

import com.example.utils.SpringUtilities;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.hamcrest.Matcher;

import javax.swing.*;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;

import static org.cthul.matchers.CthulMatchers.matchesPattern;
import static org.hamcrest.Matchers.*;

/**
 * TODO add multiple settings bundles support
 * @author innokenty
 */
class NewFtpFileListPanel extends FileListFactory<FtpFileList> {

    /**
     * based on <a href="http://stackoverflow.com/questions/1418423/the-hostname-regex">this</a>
     * stackoverflow answer but extended to allow internationalized hostnames based on an answer
     * <a href="http://stackoverflow.com/questions/14636540/java-regular-expression-with-international-letters">here</a>
     */
    private static final String HOSTNAME_REGEX
            = "^(?=.{1,255}$)[0-9\\p{IsAlphabetic}](?:(?:[0-9\\p{IsAlphabetic}]|-)" +
            "{0,61}[0-9\\p{IsAlphabetic}])?(?:\\.[0-9\\p{IsAlphabetic}]" +
            "(?:(?:[0-9\\p{IsAlphabetic}]|-){0,61}[0-9\\p{IsAlphabetic}])?)*?$";

    private static class FileTypeOptions extends LinkedHashMap<String, Integer> {

        {
            put("ASCII", FTP.ASCII_FILE_TYPE);
            put("binary", FTP.BINARY_FILE_TYPE);
            put("EBCDIC", FTP.EBCDIC_FILE_TYPE);
            put("local", FTP.LOCAL_FILE_TYPE);
        }

        public String[] getKeysAsArray() {
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

        FtpConnectionProperties props = new FtpConnectionProperties();

        JLabel hostnameLabel = new JLabel(
                "<html>hostname <font color=\"red\">*</font></html>",
                JLabel.TRAILING
        );
        add(hostnameLabel);
        hostname = new JTextField(props.getHost());
        hostnameLabel.setLabelFor(hostname);
        add(hostname);

        JLabel portLabel = new JLabel("port", JLabel.TRAILING);
        add(portLabel);
        DecimalFormat format = new DecimalFormat("##00");
        format.setMinimumIntegerDigits(1);
        format.setMaximumIntegerDigits(4);
        port = new JFormattedTextField(format);
        port.setText(String.valueOf(props.getPort()));
        portLabel.setLabelFor(port);
        add(port);

        JLabel ftpsLabel = new JLabel("ftps", JLabel.TRAILING);
        add(ftpsLabel);
        ftps = new JCheckBox();
        ftps.setSelected(props.isFtps());
        ftpsLabel.setLabelFor(ftps);
        add(ftps);

        JLabel usernameLabel = new JLabel("username", JLabel.TRAILING);
        add(usernameLabel);
        username = new JTextField(props.getUsername());
        usernameLabel.setLabelFor(username);
        add(username);

        JLabel passwordLabel = new JLabel("password", JLabel.TRAILING);
        add(passwordLabel);
        password = new JPasswordField(props.getPassword());
        passwordLabel.setLabelFor(password);
        add(password);

        JLabel fileTypeLabel = new JLabel("fileType", JLabel.TRAILING);
        add(fileTypeLabel);
        fileType = new JComboBox<>(FILE_TYPE_OPTIONS.getKeysAsArray());
        fileType.setSelectedItem(props.getFileType());
        fileTypeLabel.setLabelFor(fileType);
        add(fileType);

        SpringUtilities.makeCompactGrid(this,
                6, 2,
                6, 6,
                6, 6);
    }

    private String getHostname() {
        return hostname.getText();
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

    private boolean isFtps() {
        return ftps.isSelected();
    }

    private String getUsername() {
        return username.getText();
    }

    private String getPassword() {
        return new String(password.getPassword());
    }

    private Integer getFileType() {
        //noinspection SuspiciousMethodCalls
        return FILE_TYPE_OPTIONS.get(fileType.getSelectedItem());
    }

    @Override
    public FtpFileList buildFileList() throws Exception {
        verifyInput();
        return new FtpFileList(buildClient());
    }

    private void verifyInput() throws ListCreationException {
        assertThat(
                "hostname must not be empty!",
                getHostname(),
                is(not(isEmptyString()))
        );
        assertThat(
                "hostname is not valid! must be a combination of " +
                        "digits, letters, dots and hyphens, " +
                        "starting and ending with a letter or a digit" +
                        "and not containing two dots in a row!",
                getHostname(),
                matchesPattern(HOSTNAME_REGEX)
        );
        assertThat(
                "username and password must be either both filled or both empty!",
                getUsername().isEmpty(),
                is(equalTo(getPassword().isEmpty()))
        );
    }

    private <T> void assertThat(String message, T actual, Matcher<T> matcher)
            throws ListCreationException {
        if (!matcher.matches(actual)) {
            throw new ListCreationException(message);
        }
    }

    private FTPClient buildClient() throws Exception {
        return FtpClientFactory.buildSimpleClient(
                getHostname(),
                getPort(),
                isFtps(),
                getUsername(),
                getPassword(),
                getFileType()
        );
    }

    public static class ListCreationException extends Exception {

        public ListCreationException(String message) {
            super(message);
        }
    }
}
