package com.example.components;

import javax.swing.*;

/**
 * @author innokenty
 */
public class CenteredTextLabel extends JLabel {

    public CenteredTextLabel(String text) {
        super(
            "<html>" +
                "<div style=\"text-align: center;\">" +
                    text +
                "</div>" +
            "</html>"
        );
        setHorizontalAlignment(SwingConstants.CENTER);
    }
}
