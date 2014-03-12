package com.example.filelist;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @author innokenty
 */
public class NewFileListDialog extends JDialog {

    private FileListFactory factory;

    public NewFileListDialog(Component owner) {
        super(JOptionPane.getFrameForComponent(owner));
        setPreferredSize(new Dimension(300, 300));
        setMinimumSize(getPreferredSize());
        setMaximumSize(new Dimension(400, 300));
        setTitle("Please select new tab type");
        setModal(true);

        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        final JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add(new LocalFileListFactory());
        tabbedPane.add(new FtpFileListFactory());
        add(tabbedPane, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(new JButton(new AbstractAction("Cancel") {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewFileListDialog.this.dispose();
            }
        }));
        bottomPanel.add(new JButton(new AbstractAction("OK") {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewFileListDialog.this.dispose();
                factory = ((FileListFactory) tabbedPane.getSelectedComponent());
            }
        }));
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public FileList getFileList() throws Exception {
        pack();
        setVisible(true);
        return factory == null ? null : factory.buildFileList();
    }
}
