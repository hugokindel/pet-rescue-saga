package com.g10.prs.view.gui;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {
    public Panel() {
        super();
    }

    public Panel(LayoutManager layoutManager) {
        super(layoutManager);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, 1f));
    }
}
