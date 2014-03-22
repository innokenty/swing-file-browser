package com.example.preview;

import com.example.utils.CloseAction;

import javax.swing.*;

import static java.awt.event.ActionEvent.META_MASK;
import static java.awt.event.KeyEvent.VK_ESCAPE;
import static java.awt.event.KeyEvent.VK_W;
import static javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW;
import static javax.swing.KeyStroke.getKeyStroke;

/**
 * @author innokenty
 */
public abstract class FilePreview extends JDialog {

    public FilePreview(String fileName) {
        initUIOptions(fileName);
        bindCloseShortcuts();
    }

    private void initUIOptions(String fileName) {
        setTitle(fileName);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void bindCloseShortcuts() {
        String dispatchDialogKey = "com.example.FilePreview.dispatch";
        JRootPane root = getRootPane();
        InputMap inputMap = root.getInputMap(WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(getKeyStroke(VK_ESCAPE, 0), dispatchDialogKey);
        inputMap.put(getKeyStroke(VK_W, META_MASK), dispatchDialogKey);
        root.getActionMap().put(dispatchDialogKey, new CloseAction(this));
    }
}
