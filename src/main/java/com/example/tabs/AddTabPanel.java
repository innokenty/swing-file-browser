package com.example.tabs;

import com.example.Icon;
import com.example.TabsGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author innokenty
 */
class AddTabPanel extends JPanel {

    public AddTabPanel(final TabsGenerator tabsGenerator) {
        super();
        setName("+");
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createVerticalGlue());
        addCentered(new JLabel("Click to add new tab"));
        add(Box.createVerticalStrut(20));
        addCentered(new JLabel(Icon.PLUS.build()));
        add(Box.createVerticalGlue());
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tabsGenerator.addNewTab();
            }
        });
    }

    private void addCentered(JComponent comp) {
        comp.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(comp);
    }
}
