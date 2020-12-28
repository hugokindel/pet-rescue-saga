package com.g10.prs.view.gui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/** Container to simplify JLabel usage */
public class Label extends JLabel {
    /** class constructor */
    public Label() {
        super();
    }

    /** class constructor */
    public Label(ImageIcon imageIcon) {
        super(imageIcon);
    }

    public Label(String text) {
        super(text);
    }

    /** class constructor */
    public Label(String text, int size) {
        super(text);
        setFontSize(size);
    }

    /** class constructor */
    public Label(String text, int top, int left, int bottom, int right) {
        super(text);
        setBorder(top, left, bottom, right);
    }

    /** class constructor */
    public Label(String text, int size, int top, int left, int bottom, int right) {
        super(text);
        setFontSize(size);
        setBorder(top, left, bottom, right);
    }

    /** class constructor */
    public Label(ImageIcon imageIcon, int size) {
        super(imageIcon);
        setFontSize(size);
    }

    /** class constructor */
    public Label(ImageIcon imageIcon, int top, int left, int bottom, int right) {
        super(imageIcon);
        setBorder(top, left, bottom, right);
    }

    /** class constructor */
    public Label(ImageIcon imageIcon, int size, int top, int left, int bottom, int right) {
        super(imageIcon);
        setFontSize(size);
        setBorder(top, left, bottom, right);
    }

    /**
     * set the Font size
     *
     * @param size the size of the Font
     */
    public void setFontSize(int size) {
        setFont(new Font(getFont().getName(), getFont().getStyle(), size));
    }

    /** set the Border */
    public void setBorder(int top, int left, int bottom, int right) {
        setBorder(new CompoundBorder(getBorder(), new EmptyBorder(top, left, bottom, right)));
    }
}
