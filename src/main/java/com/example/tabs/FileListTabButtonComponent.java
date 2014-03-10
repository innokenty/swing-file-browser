package com.example.tabs;

import com.example.filelist.FileList;
import com.example.filelist.FileListModel;
import com.example.utils.AbstractListDataListener;

import javax.swing.*;
import javax.swing.event.ListDataEvent;

/**
 * @author innokenty
 */
class FileListTabButtonComponent extends TabButtonComponent {

    public FileListTabButtonComponent(final FileList fileList, JTabbedPane pane) {
        super(pane);

        final FileListModel model = fileList.getModel();
        model.addListDataListener(new AbstractListDataListener() {
            @Override
            public void contentsChanged(ListDataEvent e) {
                setText(model.getCurrentFolderName());
            }
        });
        model.fireContentsChanged();
    }
}
