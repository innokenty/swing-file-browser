package com.example.components;

import com.example.Defaults;
import com.example.Dialogs;
import com.example.FileListContainer;
import com.example.FileListSwitchListener;
import com.example.filelist.FileList;
import com.example.filelist.FileListModel;
import com.example.utils.AbstractListDataListener;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    private void setShowHiddenFiles(boolean show, FileListModel model) {
        try {
            model.setShowHiddenFiles(show);
        } catch (Exception e1) {
            Dialogs.unexpectedError(e1, listContainer.getFileList());
        }
    }

    public <T extends AbstractButton> void decorate(T button) {
        button.setEnabled(false);
        addStateChangeListener(button);
        addListChangedListener(button);
        addActionListenerToTransferFocus(button);
    }

    private <T extends AbstractButton> void addStateChangeListener(T button) {
        button.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                boolean show = e.getStateChange() == ItemEvent.SELECTED;
                setShowHiddenFiles(show, listContainer.getFileList().getModel());
            }
        });
    }

    private <T extends AbstractButton> void addListChangedListener(final T button) {
        final ListDataListener listener = buildListDataListener(button);
        listContainer.addFileListSwitchListener(new FileListSwitchListener() {
            @Override
            public void onFileListSwitched(@Nullable FileList newFileList) {
                if (newFileList == null) {
                    button.setEnabled(false);
                    return;
                }

                button.setEnabled(true);
                FileListModel model = newFileList.getModel();

                if (Defaults.BUTTON_STATE_CHANGES_ON_LIST_CHANGE) {
                    button.setSelected(model.isShowingHiddenFiles());
                } else {
                    setShowHiddenFiles(button.isSelected(), model);
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

    private <T extends AbstractButton> void addActionListenerToTransferFocus(T button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listContainer.getFileList().requestFocusInWindow();
            }
        });
    }
}
