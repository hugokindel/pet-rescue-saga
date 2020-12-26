package com.g10.prs.view.gui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/** Container to simplify JLabel usage */
public class Label {
    /** the JLabel */
    protected JLabel label;

    /** class constructor */
    public Label() {
        label = new JLabel();
    }

    /** class constructor */
    public Label(String text) {
        label = new JLabel(text);
    }

    /** class constructor */
    public Label(String text, int size) {
        label = new JLabel(text);
        setFontSize(size);
    }

    /** class constructor */
    public Label(String text, int top, int left, int bottom, int right) {
        label = new JLabel(text);
        setBorder(top, left, bottom, right);
    }

    /** class constructor */
    public Label(String text, int size, int top, int left, int bottom, int right) {
        label = new JLabel(text);
        setFontSize(size);
        setBorder(top, left, bottom, right);
    }

    /**
     * set the Font size
     *
     * @param size the size of the Font
     */
    public void setFontSize(int size) {
        label.setFont(new Font(label.getFont().getName(), label.getFont().getStyle(), size));
    }

    /** set the Border */
    public void setBorder(int top, int left, int bottom, int right) {
        Border border = label.getBorder();
        Border margin = new EmptyBorder(top, left, bottom, right);
        label.setBorder(new CompoundBorder(border, margin));
    }

    /** set the text */
    public void setText(String text) {
        label.setText(text);
    }

    /** @return the text */
    public String getText() {
        return label.getText();
    }

    /** @return the JLabel */
    public JLabel asJLabel() {
        return label;
    }
}
