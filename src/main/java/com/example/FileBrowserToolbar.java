package com.example;

import com.example.filelist.FileList;
import com.example.filelist.FileListModel;
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
        FileListModel model = fileList.getModel();
        super.add(buildGoUpButton(model));
        super.add(buildShowHiddenFilesButton(model));
        // TODO fix image sizes
    }

    private JButton buildGoUpButton(final FileListModel model) {
        final JButton button = new JButton(Icon.UP.build());
        button.setToolTipText("go up one folder");
        button.setEnabled(model.canGoUp());
        model.addListDataListener(new AbstractListDataListener() {
            @Override
            public void contentsChanged(ListDataEvent e) {
                button.setEnabled(model.canGoUp());
            }
        });
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.goUp();
            }
        });
        return button;
    }

    private JToggleButton buildShowHiddenFilesButton(final FileListModel model) {
        JToggleButton button = new JToggleButton(
                Icon.GHOST.build(), Defaults.SHOW_HIDDEN_FILES);
        button.setToolTipText("show/hide hidden files");
        button.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                model.toggleShowHiddenFiles();
            }
        });
        return button;
    }
}
