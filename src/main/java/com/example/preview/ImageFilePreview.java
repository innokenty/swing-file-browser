package com.example.preview;

import com.example.Icon;
import com.example.filelist.FileListEntry;
import com.example.utils.WindowPositioningUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Math.min;

/**
 * TODO keyboard shortcuts
 * TODO custom zoom input
 *
 * @author innokenty
 */
class ImageFilePreview extends FilePreview<ResizableImageLabel> {

    private static final int MIN_SIZE = 400;

    private static final int PREF_SIZE = 800;


    private ResizableImageLabel image;

    private JScrollPane scrollPane;


    public ImageFilePreview(FileListEntry file) {
        super(file);
    }

    @Override
    protected ResizableImageLabel load(FileListEntry file) throws Exception {
        return new ResizableImageLabel(file.getInputStream());
    }

    @Override
    protected synchronized void initUI(ResizableImageLabel image) {
        this.image = image;
        this.scrollPane = scrollPane();

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(this.scrollPane);
        panel.add(toolBar(), BorderLayout.NORTH);
        add(panel);

        pack();
        setMinimumSize(getMinimumSize());
        fitImage();

        WindowPositioningUtils.bringWindowToScreen(this);
    }

    private JScrollPane scrollPane() {
        JScrollPane scrollPane = new JScrollPane(this.image);

        Dimension minSize = this.image.getSameShapedDimension(MIN_SIZE);
        scrollPane.setMinimumSize(minSize);

        Dimension prefSize = this.image.getSameShapedDimension(PREF_SIZE);
        scrollPane.setPreferredSize(new Dimension(
                min(this.image.getImageWidth(), (int) prefSize.getWidth()),
                min(this.image.getImageHeight(), (int) prefSize.getHeight())
        ));
        return scrollPane;
    }

    private JToolBar toolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.add(zoomOutButton());
        toolBar.add(zoomInButton());
        toolBar.add(zoomToPreferredButton());
        toolBar.add(zoomToActualButton());
        return toolBar;
    }

    private void fitImage() {
        Insets insets = scrollPane.getInsets();
        image.fitIn(
                scrollPane.getWidth() - insets.left - insets.right,
                scrollPane.getHeight() - insets.top - insets.bottom
        );
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
