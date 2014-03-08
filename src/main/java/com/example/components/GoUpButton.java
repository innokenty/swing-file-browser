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
public class GoUpButton extends JButton {

    public GoUpButton(final FileListContainer container) {
        super(Icon.UP.build());
        super.setToolTipText("go up one folder");
        super.setEnabled(false);
        initActionListener(container);
        initListChangedListener(container);
    }

    private void initActionListener(final FileListContainer container) {
        super.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                container.getFileList().getModel().goUp();
            }
        });
    }

    private void initListChangedListener(FileListContainer container) {
        final AbstractListDataListener canGoUpListener
                = buildCanGoUpListener(container);

        container.onFileListChanged(new FileListWatcher() {
            @Override
            public void onFileListChanged(FileList newFileList) {
                FileListModel model = newFileList.getModel();
                GoUpButton.super.setEnabled(model.canGoUp());
                model.removeListDataListener(canGoUpListener);
                model.addListDataListener(canGoUpListener);
            }
        });
    }

    private AbstractListDataListener buildCanGoUpListener(
            final FileListContainer container) {
        return new AbstractListDataListener() {
            @Override
            public void contentsChanged(ListDataEvent e) {
                GoUpButton.super.setEnabled(
                        container.getFileList().getModel().canGoUp()
                );
            }
        };
    }
}
