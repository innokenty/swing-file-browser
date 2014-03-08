package com.example.components;

import com.example.Defaults;
import com.example.FileListContainer;
import com.example.FileListWatcher;
import com.example.filelist.FileList;
import com.example.filelist.FileListModel;
import com.example.utils.AbstractListDataListener;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author innokenty
 */
class ShowHiddenFilesButtonDecorator {

    private final FileListContainer listContainer;

    public ShowHiddenFilesButtonDecorator(FileListContainer listContainer) {
        this.listContainer = listContainer;
    }

    public <T extends AbstractButton> void decorate(T button) {
        addStateChangeListener(button);
        addListChangedListener(button);
    }

    private <T extends AbstractButton> void addStateChangeListener(T button) {
        button.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                boolean show = e.getStateChange() == ItemEvent.SELECTED;
                listContainer.getFileList().getModel().setShowHiddenFiles(show);
            }
        });
    }

    private <T extends AbstractButton> void addListChangedListener(final T button) {
        final ListDataListener listener = buildListDataListener(button);
        listContainer.onFileListChanged(new FileListWatcher() {
            @Override
            public void onFileListChanged(FileList newFileList) {
                FileListModel model = newFileList.getModel();

                if (Defaults.BUTTON_STATE_CHANGES_ON_LIST_CHANGE) {
                    button.setSelected(model.isShowingHiddenFiles());
                } else {
                    model.setShowHiddenFiles(button.isSelected());
                }

                model.removeListDataListener(listener);
                model.addListDataListener(listener);
            }
        });
    }

    private <T extends AbstractButton> ListDataListener buildListDataListener(final T button) {
        return new AbstractListDataListener() {
            @Override
            public void contentsChanged(ListDataEvent e) {
                boolean showing = listContainer.getFileList().getModel()
                        .isShowingHiddenFiles();
                if (button.isSelected() != showing) {
                    button.setSelected(showing);
                }
            }
        };
    }
}
