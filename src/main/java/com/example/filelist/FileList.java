package com.example.filelist;

import com.example.Dialogs;
import com.example.preview.FilePreview;
import com.example.preview.FilePreviewFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
        extends JPanel
        implements FileListModelSwitcher {

    private final JList<FileListEntry> delegate;

    private Stack<FileListModel> rememberedModels = new Stack<>();

    public FileList(FileListModel dataModel) {
        super(new BorderLayout());
        //noinspection unchecked
        delegate = new JList<FileListEntry>(dataModel);
        delegate.setCellRenderer(new FileListEntryRenderer());
        add(new JScrollPane(delegate));

        initTransferFocusToList();
        initOpeningSelectedOnEnter();
        initOpeningSelectedOnDoubleClick();
        initGoingUpOnBackspace();
    }


    /* WORKING WITH MODEL */

    public FileListModel getModel() {
        return (FileListModel<FileListEntry>) delegate.getModel();
    }

    private void openSelected() {
        FileListEntry file = delegate.getSelectedValue();
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
            ListModel model = new TempFolderFileListModel(tmp, file.getName(), this);
            //noinspection unchecked
            delegate.setModel(model);
        }
        return unzippedSuccessfully;
    }

    @Override
    public void switchBack() {
        //noinspection unchecked
        delegate.setModel(rememberedModels.pop());
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
                    getModel().goUp();
                } catch (Exception ex) {
                    Dialogs.unexpectedError(ex, FileList.this);
                }
            }
        };
    }
}
