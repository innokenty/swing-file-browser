package com.example.filelist;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static javax.swing.SwingConstants.LEFT;
import static org.apache.commons.io.FilenameUtils.getExtension;

/**
 * @author innokenty
 */
class FileListEntryRenderer implements ListCellRenderer<FileListEntry> {

    @Override
    public Component getListCellRendererComponent(
            JList<? extends FileListEntry> list,
            FileListEntry file,
            int index,
            boolean isSelected,
            boolean cellHasFocus
    ) {
        JLabel label = new JLabel(file.getName(), getIcon(file), LEFT);
        if (isSelected) {
            label.setOpaque(true);
            label.setBackground(list.getSelectionBackground());
            label.setForeground(list.getSelectionForeground());
        }

        return label;
    }

    private static Icon getIcon(FileListEntry file) {
        Icon icon;
        if (file.isDirectory()) {
            icon = UIManager.getDefaults().getIcon("FileView.directoryIcon");
        } else {
            try {
                File tempFile = File.createTempFile(
                        UUID.randomUUID().toString(),
                        "." + getExtension(file.getName())
                );
                tempFile.deleteOnExit();
                icon = new JFileChooser().getIcon(tempFile);
            } catch (IOException e) {
                icon = UIManager.getDefaults().getIcon("FileView.fileIcon");
            }
        }
        return icon;
    }
}
