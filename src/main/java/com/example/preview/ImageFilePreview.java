package com.example.preview;

import com.example.Icon;
import com.example.filelist.FileListEntry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Math.min;

/**
 * TODO keyboard shortcuts
 * TODO custom zoom input
 * @author innokenty
 */
class ImageFilePreview extends FilePreview {

    private static final int MIN_SIZE = 400;

    private static final int PREF_SIZE = 800;


    private final ResizableImageLabel image;

    private final JScrollPane scrollPane;


    public ImageFilePreview(FileListEntry file) throws Exception {
        super(file.getName());

        image = new ResizableImageLabel(file.getInputStream());
        scrollPane = new JScrollPane(image);

        Dimension minSize = image.getSameShapedDimension(MIN_SIZE);
        scrollPane.getViewport().setMinimumSize(minSize);

        Dimension prefSize = image.getSameShapedDimension(PREF_SIZE);
        scrollPane.getViewport().setPreferredSize(new Dimension(
                min(image.getImageWidth(), (int) prefSize.getWidth()),
                min(image.getImageHeight(), (int) prefSize.getHeight())
        ));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane);
        panel.add(toolBar(), BorderLayout.NORTH);
        add(panel);
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        if (b) {
            setMinimumSize(getMinimumSize());
            fitImage();
        }
    }

    private void fitImage() {
        Insets insets = scrollPane.getInsets();
        image.fitIn(
                scrollPane.getWidth() - insets.left - insets.right,
                scrollPane.getHeight() - insets.top - insets.bottom
        );
    }

    private JToolBar toolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.add(zoomOutButton());
        toolBar.add(zoomInButton());
        toolBar.add(zoomToPreferredButton());
        toolBar.add(zoomToActualButton());
        return toolBar;
    }

    private JButton zoomOutButton() {
        JButton zoomOutButton = new JButton(Icon.ZOOM_OUT.build());
        zoomOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                image.zoomOut();
            }
        });
        return zoomOutButton;
    }

    private JButton zoomInButton() {
        JButton zoomInButton = new JButton(Icon.ZOOM_IN.build());
        zoomInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                image.zoomIn();
            }
        });
        return zoomInButton;
    }

    private JButton zoomToPreferredButton() {
        JButton zoomToPreferredButton = new JButton(Icon.ZOOM_SHRINK.build());
        zoomToPreferredButton.setToolTipText("Fit to window");
        zoomToPreferredButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fitImage();
            }
        });
        return zoomToPreferredButton;
    }

    private JButton zoomToActualButton() {
        JButton zoomToActualButton = new JButton(Icon.ZOOM_EXPAND.build());
        zoomToActualButton.setToolTipText("Actual size");
        zoomToActualButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                image.resetZoom();
            }
        });
        return zoomToActualButton;
    }
}
