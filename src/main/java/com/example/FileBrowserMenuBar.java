package com.example;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author innokenty
 */
public class FileBrowserMenuBar extends JMenuBar {

    public FileBrowserMenuBar(FileList fileList) {
        super.add(initFileMenu());
        super.add(initViewMenu(fileList));
    }

    private JMenu initFileMenu() {
        JMenu file = new JMenu("File");
        file.setMnemonic('f');
        return file;
    }

    private JMenu initViewMenu(final FileList fileList) {
        JMenu view = new JMenu("View");
        view.setMnemonic('v');
        view.add(initShowHiddenFilesItem(fileList));
        return view;
    }

    private JCheckBoxMenuItem initShowHiddenFilesItem(final FileList fileList) {
        JCheckBoxMenuItem showHiddenFilesItem = new JCheckBoxMenuItem(
                "Show hidden files", Defaults.SHOW_HIDDEN_FILES
        );
        showHiddenFilesItem.setMnemonic('h');
        showHiddenFilesItem.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                fileList.toggleShowHiddenFiles();
            }
        });
        return showHiddenFilesItem;
    }
}
