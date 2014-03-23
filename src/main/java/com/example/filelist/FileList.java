package com.example.filelist;

import com.example.Dialogs;
import com.example.preview.FilePreview;
import com.example.preview.FilePreviewFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Stack;

import static com.example.utils.StreamUnzipper.getRandomTempFolder;
import static com.example.utils.StreamUnzipper.unzip;
import static java.awt.event.KeyEvent.VK_BACK_SPACE;
import static java.awt.event.KeyEvent.VK_ENTER;
import static javax.swing.KeyStroke.getKeyStroke;

/**
 * @author innokenty
 */
public abstract class FileList
        extends JList<FileListEntry>
        implements FileListModelSwitcher {

    private Stack<FileListModel<FileListEntry>> rememberedModels = new Stack<>();

    public FileList(FileListModel dataModel) {
        //noinspection unchecked
        super(dataModel);
        setCellRenderer(new FileListEntryRenderer());
        initOpeningSelectedOnEnter();
        initOpeningSelectedOnDoubleClick();
        initGoingUpOnBackspace();
    }


    /* MODEL METHODS OVERRIDING */

    /**
     * @param model only model of type com.example.filelist.FileListModel is supported
     * @throws java.lang.IllegalArgumentException if the passed model is not
     * instance of com.example.filelist.FileListModel
     */
    @Override
    public void setModel(ListModel<FileListEntry> model) {
        if (model instanceof FileListModel) {
            super.setModel(model);
        } else {
            throw new IllegalArgumentException("Only model of type " +
                    "com.example.filelist.FileListModel is supported");
        }
    }

    @SuppressWarnings("unchecked")
    public FileListModel<FileListEntry> getModel() {
        return (FileListModel<FileListEntry>) super.getModel();
    }

    private void openSelected() {
        FileListEntry file = super.getSelectedValue();
        try {
            if (!getModel().openFolder(file)
                    && !openFile(file)
                    && !openArchive(file)) {
                Dialogs.sorryBro("Opening this type of files is not supported!", this);
            }
        } catch (Exception e) {
            Dialogs.unexpectedError(e, this);
        }
    }

    private boolean openFile(FileListEntry file) throws Exception {
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
            ListModel model = new TempFolderFileListModel(tmp, file.getName(), this);
            //noinspection unchecked
            setModel(model);
        }
        return unzippedSuccessfully;
    }

    @Override
    public void switchBack() {
        setModel(rememberedModels.pop());
    }

    /* GUI INITIALIZATION METHODS */

    private void initOpeningSelectedOnEnter() {
        String openSelectedKey = "com.example.filelist.FileList.openSelected";
        super.getInputMap(WHEN_IN_FOCUSED_WINDOW)
                .put(getKeyStroke(VK_ENTER, 0), openSelectedKey);
        super.getActionMap().put(openSelectedKey, openSelectedAction());
    }

    private void initGoingUpOnBackspace() {
        String goUpKey = "com.example.filelist.FileList.goUp";
        super.getInputMap(WHEN_IN_FOCUSED_WINDOW)
                .put(getKeyStroke(VK_BACK_SPACE, 0), goUpKey);
        super.getActionMap().put(goUpKey, goUpAction());
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
                    getModel().goUp();
                } catch (Exception ex) {
                    Dialogs.unexpectedError(ex, FileList.this);
                }
            }
        };
    }

    private void initOpeningSelectedOnDoubleClick() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    openSelected();
                }
            }
        });
    }
}
