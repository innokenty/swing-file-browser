package com.example.tabs;

import com.example.Dialogs;
import com.example.FileListContainer;
import com.example.FileListWatcher;
import com.example.TabsGenerator;
import com.example.filelist.FileList;
import com.example.filelist.NewFileListDialog;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author innokenty
 */
public class FileListTabbedPane
        extends JTabbedPane
        implements TabsGenerator, FileListContainer {

    private final List<FileListWatcher> watchers = new ArrayList<>();

    public FileListTabbedPane() {
        add(new AddTabPanel(this));
        setEnabledAt(0, false);
        setTabComponentAt(0, new AddTabButton(this));
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
        return ((FileListScrollPane) getSelectedComponent()).getFileList();
    }

    @Override
    public void onFileListChanged(FileListWatcher watcher) {
        watchers.add(watcher);
    }

    @Override
    public void fireListChange() {
        for (FileListWatcher watcher : watchers) {
            watcher.onFileListChanged(getFileList());
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
