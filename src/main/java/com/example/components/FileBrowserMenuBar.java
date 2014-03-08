package com.example.components;

import com.example.FileListContainer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author innokenty
 */
public class FileBrowserMenuBar extends JMenuBar {

    private final FileListContainer listContainer;

    public FileBrowserMenuBar(FileListContainer container) {
        listContainer = container;
        super.add(buildFileMenu());
        super.add(buildViewMenu());
    }

    private JMenu buildFileMenu() {
        JMenu file = new JMenu("File");
        file.setMnemonic('f');
        file.add(buildQuitItem());
        return file;
    }

    private JMenuItem buildQuitItem() {
        JMenuItem quit = new JMenuItem("Quit");
        quit.setMnemonic('q');
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        return quit;
    }

    private JMenu buildViewMenu() {
        JMenu view = new JMenu("View");
        view.setMnemonic('v');
        view.add(buildShowHiddenFilesItem());
        return view;
    }

    private JMenuItem buildShowHiddenFilesItem() {
        final JCheckBoxMenuItem item = new JCheckBoxMenuItem("Show hidden files");
        item.setMnemonic('h');
        new ShowHiddenFilesButtonDecorator(listContainer).decorate(item);
        return item;
    }
}
