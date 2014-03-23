package com.example.preview;

import com.example.filelist.FileListEntry;
import com.example.utils.CloseAction;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutionException;

import static java.awt.event.ActionEvent.META_MASK;
import static java.awt.event.KeyEvent.VK_ESCAPE;
import static java.awt.event.KeyEvent.VK_W;
import static javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW;
import static javax.swing.KeyStroke.getKeyStroke;

/**
 * @author innokenty
 */
public abstract class FilePreview<T> extends JDialog {

    private static final int LOADING_SIZE = 300;

    private static final Component LOADING = new JLabel(
            "<html>" +
                "<div style=\"text-align: center;\">" +
                    "<div>Loading...</div>" +
                    "<div>Please wait.</div>" +
                "</div>" +
            "</html>"
    ) {{
        setHorizontalAlignment(SwingConstants.CENTER);
        setPreferredSize(new Dimension(LOADING_SIZE, LOADING_SIZE));
    }};

    private final FileListEntry file;


    public FilePreview(final FileListEntry file) {
        this.file = file;

        setTitle(file.getName());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        bindCloseShortcuts();

        loading(true);

        new SwingWorker<T, Void>() {
            @Override
            protected T doInBackground() throws Exception {
                synchronized (FilePreview.this.file) {
                    return load(FilePreview.this.file);
                }
            }

            @Override
            protected void done() {
                FilePreview.this.loading(false);
                try {
                    FilePreview.this.initUI(get());
                    FilePreview.this.pack();
                } catch (InterruptedException | ExecutionException e) {
                    FilePreview.this.error(e);
                }
            }
        }.execute();
    }

    private void bindCloseShortcuts() {
        String dispatchDialogKey = "com.example.FilePreview.dispatch";
        JRootPane root = getRootPane();
        InputMap inputMap = root.getInputMap(WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(getKeyStroke(VK_ESCAPE, 0), dispatchDialogKey);
        inputMap.put(getKeyStroke(VK_W, META_MASK), dispatchDialogKey);
        root.getActionMap().put(dispatchDialogKey, new CloseAction(this));
    }

    protected final void loading(boolean loading) {
        if (loading) {
            add(LOADING);
        } else {
            remove(LOADING);
        }
    }

    private void error(Exception e) {
        e.printStackTrace();
        add(new JLabel(e.getMessage(), com.example.Icon.OOPS.build(), SwingConstants.CENTER));
    }

    protected abstract T load(FileListEntry file) throws Exception;

    protected abstract void initUI(T result);
}
