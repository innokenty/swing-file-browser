package com.example.filelist;

import java.io.File;

/**
 * @author innokenty
 */
class TempFolderFileListModel extends LocalFileListModel {

    private final FileListModelSwitcher modelSwitcher;

    public TempFolderFileListModel(
            File archive, String fakeName, FileListModelSwitcher modelSwitcher
    ) throws Exception {
        super(new TempFolderFileListEntry(archive, fakeName));
        this.modelSwitcher = modelSwitcher;
    }

    @Override
    public boolean canGoUp() {
        return true;
    }

    @Override
    protected boolean goUpImpl() throws Exception {
        if (getCurrentFolder() instanceof TempFolderFileListEntry) {
            modelSwitcher.switchBack();
            return true;
        } else {
            return super.goUpImpl();
        }
    }
}
