package com.g10.prs.view.gui;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Panel extends JPanel {
    boolean paint;

    public Panel() {
        this(false);
    }

    public Panel(boolean paint) {
        super();

        this.paint = paint;
    }

    public Panel(LayoutManager layoutManager) {
        super(layoutManager);
    }

    public Panel(LayoutManager layoutManager, boolean paint) {
        super(layoutManager);

        this.paint = paint;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (paint) {
            super.paintComponent(g);
        }
    }

    public void setBorder(int top, int left, int bottom, int right) {
        setBorder(new CompoundBorder(getBorder(), new EmptyBorder(top, left, bottom, right)));
    }
}
