package com.g10.prs.view.gui;

import com.g10.prs.PetRescueSaga;
import com.g10.prs.common.print.Out;
import com.g10.prs.level.Block;
import com.g10.prs.level.Obstacle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Button extends Panel {
    JButton button;
    Label label;
    boolean mouseIn;
    boolean enabled;

    public Button(String text) {
        this(text, null);
    }

    public Button(String text, ActionListener listener) {
        if (((GuiView) PetRescueSaga.view).getStyle() == GuiView.Style.Stylized) {
            label = new Label(text);
            label.setForeground(Color.WHITE);
            setBackground(new Color(32, 129, 214));
            setLayout(new GridBagLayout());
            add(label);
            Dimension labelDim = label.getPreferredSize();
            setPreferredSize(new Dimension((int)labelDim.getWidth() + 50, (int)labelDim.getHeight() + 10));
            addActionListener(listener);
        } else {
            button = new JButton(text);
            button.addActionListener(listener);
            add(button);
        }
        enabled = true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (((GuiView) PetRescueSaga.view).getStyle() == GuiView.Style.Stylized) {
            Dimension arcs = new Dimension(32,32);
            int width = getWidth();
            int height = getHeight();
            Graphics2D graphics = (Graphics2D) g;
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            graphics.setColor(getBackground());
            graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);

            if (enabled) {
                graphics.setColor(mouseIn ? new Color(29, 123, 201).darker() : new Color(29, 123, 201));
            } else {
                graphics.setColor(mouseIn ? new Color(7, 150, 0).darker() : new Color(7, 150, 0));
            }

            graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);
            graphics.drawRoundRect(1, 1, width-3, height-3, arcs.width, arcs.height);
        }
    }

    public void addActionListener(ActionListener listener) {
        if (((GuiView) PetRescueSaga.view).getStyle() == GuiView.Style.Stylized) {
            if (getMouseListeners().length > 0) {
                removeMouseListener(getMouseListeners()[0]);
            }

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    mouseIn = true;
                    ((GuiView)PetRescueSaga.view).getWindow().repaint();
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    if (enabled) {
                        setBackground(new Color(32, 129, 214).darker());
                    } else {
                        setBackground(new Color(0, 198, 11).darker());
                    }

                    ((GuiView)PetRescueSaga.view).getWindow().repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    mouseIn = false;
                    if (enabled) {
                        setBackground(new Color(32, 129, 214));
                    }
                    ((GuiView)PetRescueSaga.view).getWindow().repaint();
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if (mouseIn && listener != null) {
                        listener.actionPerformed(new ActionEvent(this, 0, ""));
                    }
                }
            });
        } else {
            button.addActionListener(listener);
        }
    }

    public Component get() {
        if (((GuiView) PetRescueSaga.view).getStyle() == GuiView.Style.Stylized) {
            return this;
        } else {
            return button;
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;

        if (enabled) {
            setBackground(new Color(32, 129, 214));
        } else {
            setBackground(new Color(0, 198, 11));
        }

        ((GuiView)PetRescueSaga.view).getWindow().repaint();
    }
}
