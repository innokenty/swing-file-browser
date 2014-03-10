package com.example;

import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.swing.*;
import java.awt.*;

/**
 * @author innokenty
 */
// TODO fix image sizes
public class Dialogs {

    public static void unexpectedError(Exception e, Component parent) {
        JOptionPane.showMessageDialog(
                parent,
                ExceptionUtils.getStackTrace(e),
                e.getMessage(),
                JOptionPane.ERROR_MESSAGE,
                Icon.OOPS.build()
        );
    }

    public static void sorryBro(String message, Component parent) {
        JOptionPane.showMessageDialog(
                parent,
                message,
                "Sorry, bro...",
                JOptionPane.ERROR_MESSAGE,
                Icon.SORRY_BRO.build()
        );
    }
}
