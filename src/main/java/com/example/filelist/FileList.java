package com.example.filelist;

import com.example.Dialogs;
import com.example.preview.FilePreview;
import com.example.preview.FilePreviewFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import static java.awt.event.KeyEvent.VK_ENTER;
import static javax.swing.KeyStroke.getKeyStroke;

/**
 * @author innokenty
 */
public abstract class FileList<T extends FileListEntry>
        extends JList<T> {

    public FileList(FileListModel<T> dataModel) {
        super(dataModel);
        setCellRenderer(new FileListEntryRenderer());
        initOpeningSelectedOnEnter();
        initOpeningSelectedOnDoubleClick();
    }


    /* MODEL METHODS OVERRIDING */

    /**
     * @param model only model of type com.example.filelist.FileListModel is supported
     * @throws java.lang.IllegalArgumentException if the passed model is not
     * instance of com.example.filelist.FileListModel
     */
    @Override
    public void setModel(ListModel<T> model) {
        if (model instanceof FileListModel) {
            super.setModel(model);
        } else {
            throw new IllegalArgumentException("Only model of type " +
                    "com.example.filelist.FileListModel is supported");
        }
    }

    public FileListModel<T> getModel() {
        return (FileListModel<T>) super.getModel();
    }

    public void openSelected() {
        T selectedFile = super.getSelectedValue();
        if (selectedFile.isDirectory()) {
            try {
                getModel().openFolder(selectedFile);
            } catch (Exception e) {
                Dialogs.unexpectedError(e, this);
            }
        } else {
            try {
                showFilePreview();
            } catch (IOException e) {
                Dialogs.unexpectedError(e, this);
            }
        }
    }

    /* GUI INITIALIZATION METHODS */

    protected void initOpeningSelectedOnEnter() {
        final String openFolderKey = "openFolder";
        KeyStroke enterKeyStroke = getKeyStroke(VK_ENTER, 0);
        super.getInputMap().put(enterKeyStroke, openFolderKey);
        super.getActionMap().put(openFolderKey, openSelectedAction());
    }

    private AbstractAction openSelectedAction() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSelected();
            }
        };
    }

    protected void initOpeningSelectedOnDoubleClick() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    openSelected();
                }
            }
        });
    }

    protected void showFilePreview() throws IOException {
        FilePreview preview = FilePreviewFactory
                .getPreviewDialogFor(super.getSelectedValue());
        if (preview != null) {
            preview.setLocationRelativeTo(this);
            preview.pack();
            preview.setVisible(true);
        } else {
            Dialogs.sorryBro("Opening this type of files is not supported!", this);
        }
    }
}
