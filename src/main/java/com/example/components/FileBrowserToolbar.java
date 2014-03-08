package com.example.components;

import com.example.FileListContainer;
import com.example.FileListWatcher;
import com.example.Icon;
import com.example.filelist.FileList;
import com.example.filelist.FileListModel;
import com.example.utils.AbstractListDataListener;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author innokenty
 */
public class FileBrowserToolbar extends JToolBar {

    private final FileListContainer listContainer;

    public FileBrowserToolbar(FileListContainer container) {
        listContainer = container;
        super.add(buildGoUpButton());
        super.add(buildShowHiddenFilesButton());
        // TODO fix image sizes
    }

    private JButton buildGoUpButton() {
        final JButton button = new JButton(Icon.UP.build());
        button.setToolTipText("go up one folder");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listContainer.getFileList().getModel().goUp();
            }
        });

        final AbstractListDataListener canGoUpListener
                = buildCanGoUpListener(button);

        listContainer.onFileListChanged(new FileListWatcher() {
            @Override
            public void onFileListChanged(FileList newFileList) {
                FileListModel model = newFileList.getModel();
                button.setEnabled(model.canGoUp());
                model.removeListDataListener(canGoUpListener);
                model.addListDataListener(canGoUpListener);
            }
        });

        return button;
    }

    private JToggleButton buildShowHiddenFilesButton() {
        final JToggleButton button = new JToggleButton(Icon.GHOST.build());
        button.setToolTipText("Show hidden files");
        new ShowHiddenFilesButtonDecorator(listContainer).decorate(button);
        return button;
    }

    private AbstractListDataListener buildCanGoUpListener(final JButton button) {
        return new AbstractListDataListener() {
            @Override
            public void contentsChanged(ListDataEvent e) {
                button.setEnabled(
                        listContainer.getFileList().getModel().canGoUp()
                );
            }
        };
    }
}
