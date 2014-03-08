package com.example.filelist;

import com.example.preview.FilePreview;
import com.example.preview.FilePreviewFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import static java.awt.event.KeyEvent.VK_ENTER;
import static javax.swing.KeyStroke.getKeyStroke;

/**
 * @author innokenty
 */
public class LocalFileList extends JList<File> implements FileList {

    public LocalFileList() {
        this(getDefaultStartingFolder());
    }

    public LocalFileList(File startingFolder) {
        super(new LocalFileListModel(startingFolder));
        //noinspection unchecked
        setCellRenderer(filenameCellRenderer());
        initOpeningSelectedOnEnter();
        initOpeningSelectedOnDoubleClick();
    }

    private static File getDefaultStartingFolder() {
        String homeDirPath = System.getProperty("user.home");
        //noinspection ConstantConditions
        return homeDirPath != null || new File(homeDirPath).isDirectory()
                ? new File(homeDirPath)
                : new File(System.getProperty("user.dir"));
    }


    /* MODEL METHODS OVERRIDING */

    /**
     * @param model only model of type com.example.filelist.FileListModel is supported
     * @throws java.lang.IllegalArgumentException if the passed model is not
     *          instance of com.example.filelist.FileListModel
     */
    @Override
    public void setModel(ListModel<File> model) {
        if (model instanceof LocalFileListModel) {
            super.setModel(model);
        } else {
            throw new IllegalArgumentException("Only model of type " +
                    "com.example.filelist.FileListModel is supported");
        }
    }

    @Override
    public LocalFileListModel getModel() {
        return (LocalFileListModel) super.getModel();
    }


    /* OTHER INTERFACE IMPLEMENTATION METHODS */

    @Override
    public synchronized void openSelected() {
        File selectedFile = super.getSelectedValue();
        if (selectedFile.isDirectory()) {
            getModel().openFolder(selectedFile);
        } else {
            try {
                showFilePreview(selectedFile);
            } catch (IOException e) {
                reportUnableToOpenFile(e);
            }
        }
    }


    /* GUI INITIALIZATION METHODS */

    private static ListCellRenderer filenameCellRenderer() {
        return new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus
            ) {
                String fileName = ((File) value).getName();
                return super.getListCellRendererComponent(
                        list, fileName, index, isSelected, cellHasFocus);
            }
        };
    }

    private void initOpeningSelectedOnEnter() {
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

    private void initOpeningSelectedOnDoubleClick() {
        super.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    openSelected();
                }
            }
        });
    }

    private void showFilePreview(File selectedFile) throws IOException {
        FilePreview preview = new FilePreviewFactory().getPreviewDialogFor(selectedFile);
        if (preview != null) {
            preview.setLocationRelativeTo(this);
            preview.setVisible(true);
        } else {
            showFilePreviewNotSupportedError();
        }
    }


    /* DIALOG INVOCATION METHODS */

    // TODO fix image sizes
    private void showFilePreviewNotSupportedError() {
        JOptionPane.showMessageDialog(
                this,
                "Opening this type of files is not supported.",
                "Sorry, bro...",
                JOptionPane.ERROR_MESSAGE,
                com.example.Icon.SORRY_BRO.build()
        );
    }

    private void reportUnableToOpenFile(IOException e) {
        JOptionPane.showMessageDialog(
                this,
                "Oooops!",
                e.getMessage(),
                JOptionPane.ERROR_MESSAGE,
                com.example.Icon.OOPS.build()
        );
    }
}
