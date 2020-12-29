package com.g10.prs.view.gui;

import com.g10.prs.PetRescueSaga;

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
        if (paint && ((GuiView)PetRescueSaga.view).getStyle() == GuiView.Style.Stylized) {
            super.paintComponent(g);

            Dimension arcs = new Dimension(16,16);
            int width = getWidth();
            int height = getHeight();
            Graphics2D graphics = (Graphics2D) g;
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            graphics.setColor(getBackground());
            graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);
            graphics.setColor(new Color(81, 195, 239));
            graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);
            graphics.drawRoundRect(1, 1, width-3, height-3, arcs.width, arcs.height);
        }
    }

    public void setBorder(int top, int left, int bottom, int right) {
        setBorder(new CompoundBorder(getBorder(), new EmptyBorder(top, left, bottom, right)));
    }
}
