package com.example.components;

import java.awt.*;

/**
 * @author innokenty
 */
public class LoadingLabel extends CenteredTextLabel {

    private static final int LOADING_SIZE = 300;

    public LoadingLabel() {
        super(
                "<div>Loading...</div>" +
                "<div>Please wait.</div>"
        );

        setPreferredSize(new Dimension(LOADING_SIZE, LOADING_SIZE));
    }
}
