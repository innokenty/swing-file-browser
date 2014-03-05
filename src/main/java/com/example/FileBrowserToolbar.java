package com.example;

import com.example.utils.AbstractListDataListener;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author innokenty
 */
public class FileBrowserToolbar extends JToolBar {

    public FileBrowserToolbar(FileList fileList) {
        super.add(buildGoUpButton(fileList));
        super.add(buildShowHiddenFilesButton(fileList));
        // TODO fix image sizes
    }

    private JButton buildGoUpButton(final FileList fileList) {
        final JButton button = new JButton(Icon.UP.build());
        button.setToolTipText("go up one folder");
        button.setEnabled(fileList.canGoUp());
        fileList.getModel().addListDataListener(new AbstractListDataListener() {
            @Override
            public void contentsChanged(ListDataEvent e) {
                button.setEnabled(fileList.canGoUp());
            }
        });
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileList.goUp();
            }
        });
        return button;
    }

    private JToggleButton buildShowHiddenFilesButton(final FileList fileList) {
        JToggleButton button = new JToggleButton(
                Icon.GHOST.build(), Defaults.SHOW_HIDDEN_FILES);
        button.setToolTipText("show/hide hidden files");
        button.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                fileList.toggleShowHiddenFiles();
            }
        });
        return button;
    }
}
