package com.example.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

/**
 * Taken from
 * <a href="http://stackoverflow.com/questions/5812927/how-can-i-configure-a-swing-jframe-to-close-on-cmd-w">
 *     this
 * </a>
 * stackoverflow answer.
 * @see <a href="http://stackoverflow.com/questions/5812927/how-can-i-configure-a-swing-jframe-to-close-on-cmd-w">
 *          how can i configure a swing jframe to close on cmd w?
 *      </a>
 * @author kleopatra
 */
public class CloseAction extends AbstractAction {

    private Window window;
    public CloseAction(Window window) {
        this.window = window;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (window == null) return;
        window.dispatchEvent(new WindowEvent(
                window, WindowEvent.WINDOW_CLOSING));
    }

}
