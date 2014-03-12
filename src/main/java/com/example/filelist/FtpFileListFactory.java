package com.example.filelist;

import com.example.utils.SpringUtilities;

import javax.swing.*;
import java.text.DecimalFormat;

/**
 * @author innokenty
 */
class FtpFileListFactory
        extends JPanel
        implements FileListFactory<FtpFileList> {

    private final JTextField hostname;

    private final JTextField port;

    private final JCheckBox ftps;

    private final JTextField username;

    private final JPasswordField password;

    public FtpFileListFactory() {
        setName("FTP");
        setLayout(new SpringLayout());

        JLabel hostnameLabel = new JLabel("hostname", JLabel.TRAILING);
        add(hostnameLabel);
        hostname = new JTextField();
        hostnameLabel.setLabelFor(hostname);
        add(hostname);

        JLabel portLabel = new JLabel("port", JLabel.TRAILING);
        add(portLabel);
        port = new JFormattedTextField(new DecimalFormat("##00"));
        port.setText("21");
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

        SpringUtilities.makeCompactGrid(this,
                5, 2,
                6, 6,
                6, 6);
    }

    @Override
    public FtpFileList buildFileList() throws Exception {
        return new FtpFileList(
                hostname.getText(),
                Integer.parseInt(port.getText()), username.getText(),
                new String(password.getPassword()),
                ftps.isSelected()
        );
    }
}
