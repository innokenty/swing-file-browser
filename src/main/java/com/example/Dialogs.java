package com.example;

import javax.swing.*;
import java.awt.*;

/**
 * @author innokenty
 */
public class Dialogs {

    public static void sorryBro(String message, Component parent) {
        JOptionPane.showMessageDialog(
                parent,
                message,
                "Sorry, bro...",
                JOptionPane.ERROR_MESSAGE,
                Icon.SORRY_BRO.build()
        );
    }

    public static void unexpectedError(Throwable t, Component parent) {
        error(t.getMessage(), t, parent);
    }

    public static void error(String title, Throwable t, Component parent) {
        JOptionPane.showMessageDialog(
                parent,
                t.getMessage(),
                title,
                JOptionPane.ERROR_MESSAGE,
                Icon.OOPS.build()
        );
        t.printStackTrace();
    }
}
