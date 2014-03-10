package com.example.tabs;

import com.example.filelist.FtpFileList;
import com.example.utils.SpringUtilities;

import javax.swing.*;

/**
 * @author innokenty
 */
class FtpFileListFactory
        extends JPanel
        implements FileListFactory<FtpFileList> {

    private final JTextField hostname;

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
                3, 2,
                6, 6,
                6, 6);
    }

    @Override
    public FtpFileList buildFileList() throws Exception {
        return new FtpFileList(
                hostname.getText(),
                username.getText(),
                new String(password.getPassword())
        );
    }
}
