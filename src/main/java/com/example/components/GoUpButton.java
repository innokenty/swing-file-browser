package com.example.components;

import com.example.Dialogs;
import com.example.FileListContainer;
import com.example.FileListSwitchListener;
import com.example.Icon;
import com.example.filelist.FileList;
import com.example.filelist.FileListModel;
import com.example.utils.AbstractListDataListener;
import org.jetbrains.annotations.Nullable;

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
                try {
                    if (!container.getFileList().getModel().goUp()) {
                        Dialogs.sorryBro(
                                "This is the top-level folder already! " +
                                        "Or something's broken...",
                                container.getFileList()
                        );
                    } else {
                        container.getFileList().requestFocusInWindow();
                    }
                } catch (Exception ex) {
                    Dialogs.unexpectedError(ex, container.getFileList());
                }
            }
        });
    }

    private void initListChangedListener(FileListContainer container) {
        final AbstractListDataListener canGoUpListener
                = buildCanGoUpListener(container);

        container.addFileListSwitchListener(new FileListSwitchListener() {
            @Override
            public void onFileListSwitched(@Nullable FileList newFileList) {
                if (newFileList == null) {
                    GoUpButton.super.setEnabled(false);
                    return;
                }

                GoUpButton.super.setEnabled(newFileList.getModel().canGoUp());
                FileListModel model = newFileList.getModel();
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
