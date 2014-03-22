package com.example.filelist;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

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
        JLabel label = new JLabel(file.getName());
        label.setIcon(getIcon(file, label.getPreferredSize()));
        if (isSelected) {
            label.setOpaque(true);
            label.setBackground(list.getSelectionBackground());
            label.setForeground(list.getSelectionForeground());
        }

        return label;
    }

    private Icon getIcon(FileListEntry file, Dimension size) {
        Icon icon;
        if (file instanceof GoUpFileListEntry) {
            icon = com.example.Icon.UP_SMALL.build(
                    (int) size.getWidth(),
                    (int) size.getHeight()
            );
        } else if (file.isDirectory()) {
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
