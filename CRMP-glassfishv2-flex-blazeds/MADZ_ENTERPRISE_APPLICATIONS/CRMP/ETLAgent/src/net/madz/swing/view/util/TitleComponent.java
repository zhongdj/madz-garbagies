package net.madz.swing.view.util;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

public class TitleComponent {

    public static JPanel getTitleComponent(JLabel cationLabel, Color color) {
        JPanel captionPanel = new JPanel(new BorderLayout());
        if (color != null) {
            captionPanel.setBackground(color);
        }

        JPanel leftPanel = new JPanel();
        if (color != null) {
            leftPanel.setBackground(color);
        }

        JPanel rightPanel = new JPanel();
        if (color != null) {
            rightPanel.setBackground(color);
        }

        JPanel centerPanel = new JPanel();
        if (color != null) {
            centerPanel.setBackground(color);
        }

        centerPanel.setLayout(new BorderLayout());

        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new BorderLayout());
        if (color != null) {
            upperPanel.setBackground(color);
        }

        upperPanel.add(cationLabel, BorderLayout.SOUTH);

        JPanel lowerPanel = new JPanel();
        lowerPanel.setLayout(new BorderLayout());
        if (color != null) {
            lowerPanel.setBackground(color);
        }

        lowerPanel.add(new JSeparator(), BorderLayout.NORTH);

        centerPanel.add(upperPanel, BorderLayout.NORTH);
        centerPanel.add(lowerPanel, BorderLayout.SOUTH);

        // captionPanel.setLayout(new FlowLayout());
        captionPanel.add(leftPanel, BorderLayout.WEST);
        captionPanel.add(centerPanel, BorderLayout.CENTER);
        captionPanel.add(rightPanel, BorderLayout.EAST);

        return captionPanel;
    }
}
