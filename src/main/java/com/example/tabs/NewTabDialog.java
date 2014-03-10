package com.example.tabs;

import com.example.filelist.FileList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @author innokenty
 */
class NewTabDialog extends JDialog {

    private FileListFactory factory;

    public NewTabDialog(Component owner) {
        super(JOptionPane.getFrameForComponent(owner));
        setPreferredSize(new Dimension(300, 200));
        setMinimumSize(getPreferredSize());
        setMaximumSize(new Dimension(400, 250));
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
                NewTabDialog.this.dispose();
            }
        }));
        bottomPanel.add(new JButton(new AbstractAction("OK") {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewTabDialog.this.dispose();
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
