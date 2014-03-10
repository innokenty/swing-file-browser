package com.example.tabs;

import com.example.filelist.LocalFileList;

import javax.swing.*;
import java.awt.*;

/**
 * @author innokenty
 */
class LocalFileListFactory
        extends JPanel
        implements FileListFactory<LocalFileList> {

    public LocalFileListFactory() {
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
