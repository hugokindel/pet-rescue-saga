package com.g10.prs.view.gui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Label {
    protected JLabel label;

    public Label() {
        label = new JLabel();
    }

    public Label(String text) {
        label = new JLabel(text);
    }

    public Label(String text, int size) {
        label = new JLabel(text);
        setFontSize(size);
    }

    public Label(String text, int top, int left, int bottom, int right) {
        label = new JLabel(text);
        setBorder(top, left, bottom, right);
    }

    public Label(String text, int size, int top, int left, int bottom, int right) {
        label = new JLabel(text);
        setFontSize(size);
        setBorder(top, left, bottom, right);
    }

    public void setFontSize(int size) {
        label.setFont(new Font(label.getFont().getName(), label.getFont().getStyle(), size));
    }

    public void setBorder(int top, int left, int bottom, int right) {
        Border border = label.getBorder();
        Border margin = new EmptyBorder(top, left, bottom, right);
        label.setBorder(new CompoundBorder(border, margin));
    }

    public void setText(String text) {
        label.setText(text);
    }

    public String getText() {
        return label.getText();
    }

    public JLabel asJLabel() {
        return label;
    }
}
