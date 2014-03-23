package com.example.tabs;

import com.example.Dialogs;
import com.example.FileListContainer;
import com.example.FileListSwitchListener;
import com.example.TabsGenerator;
import com.example.filelist.FileList;
import com.example.filelist.NewFileListDialog;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO add shortcuts for new tab, close tab, change tab actions
 * @author innokenty
 */
public class FileListTabbedPane
        extends JTabbedPane
        implements TabsGenerator, FileListContainer {

    private final List<FileListSwitchListener> watchers = new ArrayList<>();

    public FileListTabbedPane() {
        add(new AddTabPanel(this));
        setEnabledAt(0, false);
        setTabComponentAt(0, new AddTabButton(this));
        addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (getSelectedIndex() == getTabCount() - 1 && getTabCount() > 1) {
                    setSelectedIndex(getSelectedIndex() - 1);
                }
                fireListSwitched();
            }
        });
    }

    @Override
    public void addNewTab() {
        try {
            FileList list = new NewFileListDialog(this).getFileList();
            if (list != null) {
                addNewTab(list);
            }
        } catch (Exception e) {
            Dialogs.unexpectedError(e, this);
        }
    }

    private void addNewTab(FileList fileList) {
        int index = getTabCount() - 1;
        insertTab(null, null, fileList, null, index);
        setTabComponentAt(index, new FileListTabButtonComponent(fileList, this));
        setSelectedIndex(index);
        fireListSwitched();
        getFileList().requestFocusInWindow();
    }

    @Override
    public FileList getFileList() {
        Component selected = getSelectedComponent();
        if (selected instanceof FileList) {
            return (FileList) selected;
        } else {
            return null;
        }
    }

    @Override
    public void addFileListSwitchListener(FileListSwitchListener watcher) {
        watchers.add(watcher);
    }

    @Override
    public void fireListSwitched() {
        FileList list = getFileList();
        for (FileListSwitchListener watcher : watchers) {
            watcher.onFileListSwitched(list);
        }
    }
}
