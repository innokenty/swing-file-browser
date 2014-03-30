package com.example.tabs;

import com.example.filelist.FileList;
import com.example.utils.AbstractListDataListener;

import javax.swing.*;
import javax.swing.event.ListDataEvent;

/**
 * @author innokenty
 */
class FileListTabButtonComponent extends TabButtonComponent {

    public FileListTabButtonComponent(final FileList fileList, JTabbedPane pane) {
        super(pane);

        fileList.addListDataListener(new AbstractListDataListener() {
            @Override
            public void contentsChanged(ListDataEvent e) {
                updateFolderName(fileList);
            }
        });
        updateFolderName(fileList);
    }

    private void updateFolderName(FileList fileList) {
        setText(fileList.getCurrentFolderName());
    }
}
