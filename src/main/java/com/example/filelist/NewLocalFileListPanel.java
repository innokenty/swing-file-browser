package com.example.filelist;

import com.example.components.CenteredTextLabel;

import javax.swing.*;
import java.awt.*;

/**
 * @author innokenty
 */
class NewLocalFileListPanel extends FileListFactory<LocalFileList> {

    public NewLocalFileListPanel() {
        setName("Local");
        setLayout(new BorderLayout());
        JLabel label = new CenteredTextLabel(
                "Create a simple local file browser starting at the user home directory."
        );
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label);
    }

    @Override
    public LocalFileList buildFileList() throws Exception {
        return new LocalFileList();
    }
}
