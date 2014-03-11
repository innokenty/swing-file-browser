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
    public String getCurrentFolderName() {
        String name = currentFolder.getName();
        return name.isEmpty() ? "/" : name;
    }

    @Override
    protected void openFolderImpl(LocalFileListEntry folder) {
        this.currentFolder = folder;
    }

    protected Iterable<LocalFileListEntry> listFiles() {
        return currentFolder.listFiles(showHiddenFiles);
    }

    @Override
    public boolean canGoUp() {
        return currentFolder.getParentFile() != null;
    }

    @Override
    protected boolean goUpImpl() throws Exception {
        openFolder(currentFolder.getParentFile());
        return true;
    }

    @Override
    public boolean isShowingHiddenFiles() {
        return showHiddenFiles;
    }

    @Override
    protected void setShowHiddenFilesImpl(boolean showHiddenFiles) {
        this.showHiddenFiles = showHiddenFiles;
    }
}
