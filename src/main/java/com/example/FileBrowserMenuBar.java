package com.example;

import com.example.filelist.FileList;
import com.example.filelist.FileListModel;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author innokenty
 */
public class FileBrowserMenuBar extends JMenuBar {

    public FileBrowserMenuBar(FileList fileList) {
        super.add(buildFileMenu());
        super.add(buildViewMenu(fileList.getModel()));
    }

    private JMenu buildFileMenu() {
        JMenu file = new JMenu("File");
        file.setMnemonic('f');
        return file;
    }

    private JMenu buildViewMenu(FileListModel model) {
        JMenu view = new JMenu("View");
        view.setMnemonic('v');
        view.add(buildShowHiddenFilesItem(model));
        return view;
    }

    private JCheckBoxMenuItem buildShowHiddenFilesItem(final FileListModel model) {
        JCheckBoxMenuItem showHiddenFilesItem = new JCheckBoxMenuItem(
                "Show hidden files", Defaults.SHOW_HIDDEN_FILES
        );
        showHiddenFilesItem.setMnemonic('h');
        showHiddenFilesItem.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                model.toggleShowHiddenFiles();
            }
        });
        return showHiddenFilesItem;
    }
}
