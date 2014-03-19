package com.example.filelist;

import com.example.Dialogs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * @author innokenty
 */
public class NewFileListDialog extends JDialog {

    private static final List<? extends FileListFactory> FACTORIES = asList(
            new NewLocalFileListPanel(),
            new NewFtpFileListPanel()
    );

    private FileList fileList;

    public NewFileListDialog(Component owner) {
        super(JOptionPane.getFrameForComponent(owner));
        initUIOptions();

        JTabbedPane tabbedPane = listSelectionPane();
        add(tabbedPane, BorderLayout.NORTH);
        add(bottomPanel(tabbedPane), BorderLayout.SOUTH);
    }

    private void initUIOptions() {
        setTitle("Please select new tab type");
        setPreferredSize(new Dimension(300, 300));
        setResizable(false);
        setModal(true);
        setLayout(new BorderLayout());
    }

    private JTabbedPane listSelectionPane() {
        final JTabbedPane tabbedPane = new JTabbedPane();
        for (FileListFactory factory : FACTORIES) {
            tabbedPane.add(factory);
        }
        return tabbedPane;
    }

    private JPanel bottomPanel(JTabbedPane tabbedPane) {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(cancelButton());
        bottomPanel.add(okButton(tabbedPane));
        return bottomPanel;
    }

    private JButton okButton(final JTabbedPane tabbedPane) {
        return new JButton(new AbstractAction("OK") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    fileList = ((FileListFactory) tabbedPane.getSelectedComponent())
                            .buildFileList();
                    NewFileListDialog.this.dispose();
                } catch (Exception ex) {
                    Dialogs.error(
                            "Unable to create list!", ex, NewFileListDialog.this
                    );
                }
            }
        });
    }

    private JButton cancelButton() {
        return new JButton(new AbstractAction("Cancel") {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewFileListDialog.this.dispose();
            }
        });
    }

    public FileList getFileList() throws Exception {
        pack();
        setLocationRelativeTo(getOwner());
        setVisible(true);
        return fileList == null ? null : fileList;
    }
}
