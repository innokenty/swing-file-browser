package com.example.filelist;

import com.example.Defaults;

import java.io.File;

/**
 * @author innokenty
 */
class LocalFileListModel extends FileListModel<LocalFileListEntry> {

    private boolean showHiddenFiles = Defaults.SHOW_HIDDEN_FILES;

    private LocalFileListEntry currentFolder;

    public LocalFileListModel(File startingFolder) throws Exception {
        this(new LocalFileListEntry(startingFolder));
    }

    public LocalFileListModel(LocalFileListEntry currentFolder) throws Exception {
        openFolder(currentFolder);
    }

    @Override
    public boolean canGoUp() {
        return currentFolder.getParentFile() != null;
    }

    @Override
    public boolean isShowingHiddenFiles() {
        return showHiddenFiles;
    }

    @Override
    protected void openFolderImpl(LocalFileListEntry folder) {
        this.currentFolder = folder;
    }

    @Override
    protected boolean goUpImpl() throws Exception {
        openFolder(currentFolder.getParentFile());
        return true;
    }

    @Override
    protected void setShowHiddenFilesImpl(boolean showHiddenFiles) {
        this.showHiddenFiles = showHiddenFiles;
    }

    protected Iterable<LocalFileListEntry> listFiles() {
        return currentFolder.listFiles(showHiddenFiles);
    }

    @Override
    public String getCurrentFolderName() {
        String name = currentFolder.getName();
        return name.isEmpty() ? "/" : name;
    }
}
