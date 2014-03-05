package com.example;

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
public class FileList extends JList<File> {

    private static final String OPEN_FOLDER_KEY = "openFolder";

    private final FilePreviewFactory previewFactory = new FilePreviewFactory();

    public FileList() {
        this(getDefaultStartingFolder());
    }

    public FileList(File startingFolder) {
        this(new FileListModel(startingFolder));
    }

    public FileList(FileListModel dataModel) {
        super(dataModel);
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


    /* OVERRIDE METHODS WORKING WITH MODEL */

    /**
     * @param model only model of type com.example.FileListModel is supported
     * @throws java.lang.IllegalArgumentException if the passed model is not
     *          instance of com.example.FileListModel
     */
    @Override
    public void setModel(ListModel<File> model) {
        if (model instanceof FileListModel) {
            super.setModel(model);
        } else {
            throw new IllegalArgumentException("Only model of type " +
                    "com.example.FileListModel is supported");
        }
    }

    @Override
    public FileListModel getModel() {
        return (FileListModel) super.getModel();
    }


    /* DELEGATE MODEL METHODS */

    public boolean canGoUp() {
        return getModel().canGoUp();
    }

    public void goUp() {
        getModel().goUp();
    }

    public void setShowHiddenFiles(boolean showHiddenFiles) {
        getModel().setShowHiddenFiles(showHiddenFiles);
    }

    public void toggleShowHiddenFiles() {
        getModel().toggleShowHiddenFiles();
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
        KeyStroke enterKeyStroke = getKeyStroke(VK_ENTER, 0);
        super.getInputMap().put(enterKeyStroke, OPEN_FOLDER_KEY);
        super.getActionMap().put(OPEN_FOLDER_KEY, openSelectedAction());
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

    private synchronized void openSelected() {
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

    private void showFilePreview(File selectedFile) throws IOException {
        FilePreview preview = previewFactory.getPreviewDialogFor(selectedFile);
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
                Icon.SORRY_BRO.build()
        );
    }

    private void reportUnableToOpenFile(IOException e) {
        JOptionPane.showMessageDialog(
                this,
                "Oooops!",
                e.getMessage(),
                JOptionPane.ERROR_MESSAGE,
                Icon.OOPS.build()
        );
    }
}
