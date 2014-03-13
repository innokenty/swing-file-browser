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

    public void openSelected() {
        FileListEntry selectedFile = super.getSelectedValue();
        try {
            //noinspection unchecked
            if (!getModel().openFolder(selectedFile)) {
                FilePreview preview = FilePreviewFactory
                        .getPreviewDialogFor(selectedFile);
                if (preview != null) {
                    preview.setLocationRelativeTo(this);
                    preview.pack();
                    preview.setVisible(true);
                } else if (!openArchive(selectedFile)){
                    Dialogs.sorryBro("Opening this type of files is not supported!", this);
                }
            }
        } catch (Exception e) {
            Dialogs.unexpectedError(e, this);
        }
    }

    private boolean openArchive(FileListEntry selectedFile) throws Exception {
        File tmp = getRandomTempFolder();
        tmp.deleteOnExit();
        boolean unzippedSuccessfully = unzip(selectedFile.getInputStream(), tmp);
        if (unzippedSuccessfully) {
            rememberedModels.push(getModel());
            ListModel model = new TempFolderFileListModel(tmp, selectedFile.getName(), this);
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

    protected void initOpeningSelectedOnEnter() {
        final String openFolderKey = "openSelectedFile";
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
}
