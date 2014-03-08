package com.example.components;

import com.example.FileListContainer;
import com.example.Icon;

import javax.swing.*;

/**
 * @author innokenty
 */
public class ShowHiddenFilesButton extends JToggleButton {

    public ShowHiddenFilesButton(FileListContainer container) {
        super(Icon.GHOST.build());
        super.setToolTipText("Show hidden files");
        new ShowHiddenFilesButtonDecorator(container).decorate(this);
    }
}
