package net.madz.swing.wizard;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.LayoutManager;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

class LogoPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private ImageIcon image = null;

    public LogoPanel(ImageIcon image) {
        setLayout(new BorderLayout(0, 0));
        this.image = image;
    }

    public LogoPanel(LayoutManager layout, ImageIcon image) {
        super(layout);
        this.image = image;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image.getImage(), 0, 0, null, null);

    }

}
