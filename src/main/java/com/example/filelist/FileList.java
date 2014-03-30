package com.example.filelist;

import com.example.Dialogs;
import com.example.components.LoadingLabel;
import com.example.preview.FilePreview;
import com.example.preview.FilePreviewFactory;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Stack;
import java.util.concurrent.ExecutionException;

import static com.example.utils.StreamUnzipper.getRandomTempFolder;
import static com.example.utils.StreamUnzipper.unzip;
import static java.awt.event.KeyEvent.VK_BACK_SPACE;
import static java.awt.event.KeyEvent.VK_ENTER;
import static javax.swing.KeyStroke.getKeyStroke;

/**
 * @author innokenty
 */
public abstract class FileList
        extends JPanel
        implements FileListModelSwitcher {

    private static final String FILE_LIST_CARD = "file list";

    private static final String LOADING_LABEL_CARD = "loading label";

    private final JList<FileListEntry> delegate;

    private final Stack<FileListModel> rememberedModels = new Stack<>();

    public FileList(FileListModel dataModel) {
        super(new CardLayout());
        //noinspection unchecked
        delegate = new JList<FileListEntry>(dataModel);
        delegate.setCellRenderer(new FileListEntryRenderer());
        add(new JScrollPane(delegate), FILE_LIST_CARD);
        add(new LoadingLabel(), LOADING_LABEL_CARD);
        loading(false);

        initTransferFocusToList();
        initOpeningSelectedOnEnter();
        initOpeningSelectedOnDoubleClick();
        initGoingUpOnBackspace();
    }

    private void loading(boolean loading) {
        CardLayout layout = (CardLayout) getLayout();
        if (loading) {
            layout.show(this, LOADING_LABEL_CARD);
        } else {
            layout.show(this, FILE_LIST_CARD);
            delegate.requestFocusInWindow();
        }
    }

    /* WORKING WITH MODEL */

    public FileListModel getModel() {
        return (FileListModel<FileListEntry>) delegate.getModel();
    }

    private void setModel(FileListModel newModel) {
        newModel.addListDataListeners(getModel().getListDataListeners());
        //noinspection unchecked
        delegate.setModel(newModel);
        fireContentsChanged();
    }

    private void openSelected() {
        loading(true);
        new OpenSelectedWorker(delegate.getSelectedValue(), this).execute();
    }

    private boolean openFile(FileListEntry file) {
        FilePreview preview = FilePreviewFactory.getPreviewDialogFor(file, this);
        if (preview != null) {
            preview.setVisible(true);
            return true;
        }
        return false;
    }

    private boolean openArchive(FileListEntry file) throws Exception {
        File tmp = getRandomTempFolder();
        tmp.deleteOnExit();
        boolean unzippedSuccessfully = unzip(file.getInputStream(), tmp);
        if (unzippedSuccessfully) {
            rememberedModels.push(getModel());
            setModel(new TempFolderFileListModel(tmp, file.getName(), this));
        }
        return unzippedSuccessfully;
    }

    @Override
    public void switchBack() {
        setModel(rememberedModels.pop());
    }


    /* DELEGATED MODEL METHODS */

    public boolean canGoUp() {
        return getModel().canGoUp();
    }

    public boolean goUp() throws Exception {
        return getModel().goUp();
    }

    public boolean isShowingHiddenFiles() {
        return getModel().isShowingHiddenFiles();
    }

    public void setShowHiddenFiles(boolean showHiddenFiles) throws Exception {
        getModel().setShowHiddenFiles(showHiddenFiles);
    }

    public String getCurrentFolderName() {
        return getModel().getCurrentFolderName();
    }

    public void addListDataListener(ListDataListener l) {
        getModel().addListDataListener(l);
    }

    public void removeListDataListener(ListDataListener l) {
        getModel().removeListDataListener(l);
    }

    public void fireContentsChanged() {
        getModel().fireContentsChanged();
    }

    /* GUI INITIALIZATION METHODS */

    private void initTransferFocusToList() {
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                delegate.requestFocusInWindow();
            }
        });
    }

    private void initOpeningSelectedOnEnter() {
        String openSelectedKey = "com.example.filelist.FileList.openSelected";
        delegate.getInputMap(WHEN_IN_FOCUSED_WINDOW)
                .put(getKeyStroke(VK_ENTER, 0), openSelectedKey);
        delegate.getActionMap().put(openSelectedKey, openSelectedAction());
    }

    private void initOpeningSelectedOnDoubleClick() {
        delegate.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    openSelected();
                }
            }
        });
    }

    private void initGoingUpOnBackspace() {
        String goUpKey = "com.example.filelist.FileList.goUp";
        delegate.getInputMap(WHEN_IN_FOCUSED_WINDOW)
                .put(getKeyStroke(VK_BACK_SPACE, 0), goUpKey);
        delegate.getActionMap().put(goUpKey, goUpAction());
    }

    private Action openSelectedAction() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSelected();
            }
        };
    }

    private Action goUpAction() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    goUp();
                } catch (Exception ex) {
                    Dialogs.unexpectedError(ex, FileList.this);
                }
            }
        };
    }

    private class OpenSelectedWorker extends SwingWorker<Boolean, Void> {

        private FileListEntry file;

        private Component parent;

        private OpenSelectedWorker(FileListEntry file, Component parent) {
            this.file = file;
            this.parent = parent;
        }

        @Override
        protected Boolean doInBackground() throws Exception {
            return getModel().openFolder(file)
                    || openFile(file)
                    || openArchive(file);
        }

        @Override
        protected void done() {
            loading(false);
            try {
                if (!get()) {
                    Dialogs.sorryBro(
                            "Opening this type of files is not supported!",
                            parent
                    );
                }
            } catch (InterruptedException e) {
                Dialogs.unexpectedError(e, parent);
            } catch (ExecutionException e) {
                Dialogs.unexpectedError(e.getCause(), parent);
            }
        }
    }
}
