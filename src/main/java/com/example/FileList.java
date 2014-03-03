package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileFilter;

import static java.awt.event.KeyEvent.VK_ENTER;
import static javax.swing.KeyStroke.getKeyStroke;

/**
 * @author innokenty
 */
public class FileList extends JList<File> {

    private static final String OPEN_FOLDER_KEY = "openFolder";

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
        openSelectedFolderOnEnter();
        openSelectedFolderOnDoubleClick();
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

    private void openSelectedFolderOnEnter() {
        KeyStroke enterKeyStroke = getKeyStroke(VK_ENTER, 0);
        super.getInputMap().put(enterKeyStroke, OPEN_FOLDER_KEY);
        super.getActionMap().put(OPEN_FOLDER_KEY, openSelectedFolderAction());
    }

    private AbstractAction openSelectedFolderAction() {
        return new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openSelectedFolder();
                }
            };
    }

    private void openSelectedFolderOnDoubleClick() {
        super.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    openSelectedFolder();
                }
            }
        });
    }

    public synchronized void openSelectedFolder() {
        currentFolder = super.getSelectedValue();
        render();
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
