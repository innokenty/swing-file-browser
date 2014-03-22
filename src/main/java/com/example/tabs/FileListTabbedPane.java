package com.example.tabs;

import com.example.Dialogs;
import com.example.FileListChangeListener;
import com.example.FileListContainer;
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
 * @author innokenty
 */
public class FileListTabbedPane
        extends JTabbedPane
        implements TabsGenerator, FileListContainer {

    private final List<FileListChangeListener> watchers = new ArrayList<>();

    public FileListTabbedPane() {
        add(new AddTabPanel(this));
        setEnabledAt(0, false);
        setTabComponentAt(0, new AddTabButton(this));
        addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (getSelectedIndex() == getTabCount() - 1 && getTabCount() > 1) {
                    setSelectedIndex(getSelectedIndex() - 1);
                }
                fireListChange();
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
        insertTab(null, null, new FileListScrollPane(fileList), null, index);
        setTabComponentAt(index, new FileListTabButtonComponent(fileList, this));
        setSelectedIndex(index);
        fireListChange();
        getFileList().requestFocusInWindow();
    }

    @Override
    public FileList getFileList() {
        Component selected = getSelectedComponent();
        if (selected instanceof FileListScrollPane) {
            return ((FileListScrollPane) selected).getFileList();
        } else {
            return null;
        }
    }

    @Override
    public void addFileListChangeListener(FileListChangeListener watcher) {
        watchers.add(watcher);
    }

    @Override
    public void fireListChange() {
        FileList list = getFileList();
        for (FileListChangeListener watcher : watchers) {
            watcher.onFileListChanged(list);
        }
    }

    private class FileListScrollPane extends JScrollPane {

        private final FileList fileList;

        public FileListScrollPane(FileList fileList) {
            super(fileList);
            this.fileList = fileList;
        }

        public FileList getFileList() {
            return fileList;
        }
    }
}
