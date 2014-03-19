package com.example;

import javax.swing.*;
import java.awt.*;

/**
 * @author innokenty
 */
// TODO fix image sizes
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

    public static void unexpectedError(Exception e, Component parent) {
        error(e.getMessage(), e, parent);
    }

    public static void error(String title, Throwable e, Component parent) {
        JOptionPane.showMessageDialog(
                parent,
                e.getMessage(),
                title,
                JOptionPane.ERROR_MESSAGE,
                Icon.OOPS.build()
        );
        e.printStackTrace();
    }
}
