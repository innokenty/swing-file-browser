package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import static java.awt.event.KeyEvent.VK_ENTER;
import static javax.swing.KeyStroke.getKeyStroke;

/**
 * @author innokenty
 */
public class FileList extends JList<File> {

    private static final String OPEN_FOLDER_KEY = "openFolder";

    private final FilePreviewFactory previewFactory = new FilePreviewFactory();

    private boolean showHiddenFiles = Defaults.SHOW_HIDDEN_FILES;

    private File currentFolder;

    public FileList() {
        this(getDefaultStartingFolder());
    }

    public FileList(File startingFolder) {
        super();
        currentFolder = startingFolder;
        //noinspection unchecked
        setCellRenderer(filenameCellRenderer());
        openSelectedOnEnter();
        openSelectedOnDoubleClick();
        render();
    }

    private static File getDefaultStartingFolder() {
        String homeDirPath = System.getProperty("user.home");
        //noinspection ConstantConditions
        return homeDirPath != null || new File(homeDirPath).isDirectory()
                ? new File(homeDirPath)
                : new File(System.getProperty("user.dir"));
    }

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

    private void openSelectedOnEnter() {
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

    private void openSelectedOnDoubleClick() {
        super.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    openSelected();
                }
            }
        });
    }

    public synchronized void openSelected() {
        File selectedFile = super.getSelectedValue();
        if (selectedFile.isDirectory()) {
            currentFolder = selectedFile;
            render();
        } else {
            try {
                showFilePreview(selectedFile);
            } catch (IOException e) {
                reportFileTypeNotSupported(e);
            }
        }
    }

    private void showFilePreview(File selectedFile) throws IOException {
        FilePreview preview = previewFactory.getPreviewDialogFor(selectedFile);
        if (preview != null) {
            preview.setLocationRelativeTo(this);
            preview.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Opening this type of files is not supported.",
                    "Sorry, bro...",
                    JOptionPane.ERROR_MESSAGE,
                    new ImageIcon(this.getClass().getClassLoader()
                            .getResource("img/sorry-bro.jpg"))
            );
        }
    }

    private void reportFileTypeNotSupported(IOException e) {
        JOptionPane.showMessageDialog(
                this,
                "Oooops!",
                e.getMessage(),
                JOptionPane.ERROR_MESSAGE,
                new ImageIcon(this.getClass().getClassLoader()
                        .getResource("img/oops.jpg"))
        );
    }

    private void render() {
        super.setListData(currentFolder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return showHiddenFiles || !file.isHidden();
            }
        }));
    }

    public void setShowHiddenFiles(boolean showHiddenFiles) {
        if (this.showHiddenFiles != showHiddenFiles) {
            this.showHiddenFiles = showHiddenFiles;
            render();
        } else {
            this.showHiddenFiles = showHiddenFiles;
        }
    }

    public void toggleShowHiddenFiles() {
        setShowHiddenFiles(!showHiddenFiles);
    }
}
