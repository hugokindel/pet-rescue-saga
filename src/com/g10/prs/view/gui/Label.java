package com.g10.prs.view.gui;

import com.g10.prs.common.Resources;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.Arrays;
import java.util.Optional;

import static com.g10.prs.view.gui.GuiMenu.getView;

/** Container to simplify JLabel usage */
public class Label extends JLabel {
    /** class constructor */
    public Label() {
        super();
    }

    /** class constructor */
    public Label(ImageIcon imageIcon) {
        this(imageIcon, 18, 0, 0, 0, 0);
    }

    public Label(String text) {
        this(text, 18, 0, 0, 0, 0);
    }

    /** class constructor */
    public Label(String text, int size) {
        this(text, size, 0, 0, 0, 0);
    }

    /** class constructor */
    public Label(String text, int top, int left, int bottom, int right) {
        this(text, 18, top, left, bottom, right);
    }

    /** class constructor */
    public Label(String text, int size, int top, int left, int bottom, int right) {
        super(text);
        if (getView().getStyle() == GuiView.Style.Stylized) {
            setFont("Curse Casual Regular");
            setForeground(new Color(14, 138, 164));
        }
        setFontSize(size);
        setBorder(top, left, bottom, right);
    }

    /** class constructor */
    public Label(ImageIcon imageIcon, int size) {
        this(imageIcon, size, 0, 0, 0, 0);
    }

    /** class constructor */
    public Label(ImageIcon imageIcon, int top, int left, int bottom, int right) {
        this(imageIcon, 18, top, left, bottom, right);
    }

    /** class constructor */
    public Label(ImageIcon imageIcon, int size, int top, int left, int bottom, int right) {
        super(imageIcon);
        if (getView().getStyle() == GuiView.Style.Stylized) {
            setFont("Curse Casual Regular");
            setForeground(new Color(14, 138, 164));
        }
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

    public void setFont(String name) {
        GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        setFont(Arrays.stream(gEnv.getAllFonts()).filter(i -> i.getName().equals(name)).findFirst().get().deriveFont(18f));
    }
}
