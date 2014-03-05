package com.example;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author innokenty
 */
public class FileBrowserMenuBar extends JMenuBar {

    public FileBrowserMenuBar(FileList fileList) {
        super.add(buildFileMenu());
        super.add(buildViewMenu(fileList));
    }

    private JMenu buildFileMenu() {
        JMenu file = new JMenu("File");
        file.setMnemonic('f');
        return file;
    }

    private JMenu buildViewMenu(final FileList fileList) {
        JMenu view = new JMenu("View");
        view.setMnemonic('v');
        view.add(buildShowHiddenFilesItem(fileList));
        return view;
    }

    private JCheckBoxMenuItem buildShowHiddenFilesItem(final FileList fileList) {
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
