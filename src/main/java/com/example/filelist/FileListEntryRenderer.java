package com.example.filelist;

import javax.swing.*;
import java.awt.*;

/**
 * @author innokenty
 */
class FileListEntryRenderer implements ListCellRenderer<FileListEntry> {

    @Override
    public Component getListCellRendererComponent(
            JList<? extends FileListEntry> list,
            FileListEntry value,
            int index,
            boolean isSelected,
            boolean cellHasFocus
    ) {
        JLabel label = new JLabel(value.getName());
        if (isSelected) {
            label.setOpaque(true);
            label.setBackground(list.getSelectionBackground());
            label.setForeground(list.getSelectionForeground());
        }

        return label;
    }
}
