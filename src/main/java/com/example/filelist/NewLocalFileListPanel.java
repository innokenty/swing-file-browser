package com.example.filelist;

import javax.swing.*;
import java.awt.*;

/**
 * @author innokenty
 */
class NewLocalFileListPanel extends FileListFactory<LocalFileList> {

    public NewLocalFileListPanel() {
        setName("Local");
        setLayout(new BorderLayout());
        JLabel label = new JLabel("<html>" +
                "<div style=\"text-align: center;\">" +
                "Create a simple local file " +
                "browser starting at the user home directory." +
                "</div>" +
                "</html>");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label);
    }

    @Override
    public LocalFileList buildFileList() throws Exception {
        return new LocalFileList();
    }
}
