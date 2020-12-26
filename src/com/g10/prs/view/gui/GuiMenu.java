package com.g10.prs.view.gui;

import com.g10.prs.PetRescueSaga;
import com.g10.prs.common.Pair;
import com.g10.prs.view.Menu;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

/** base class for Gui Menu */
public abstract class GuiMenu extends Menu {
    /** panel with the content */
    JPanel panel;
    /** the action that can be made in the menu */
    Pair<String, ActionListener>[] categories;

    /** class constructor */
    public GuiMenu(String title, Pair<String, ActionListener>[] categories, boolean canGoBack) {
        super(title, canGoBack);

        this.categories = categories;
    }

    /** class constructor */
    public GuiMenu(String title, boolean canGoBack) {
        this(title, null, canGoBack);
    }

    /** class constructor */
    public GuiMenu(String title) {
        this(title, null, true);
    }

    /** show the Menu */
    @Override
    public void draw() {
        panel = new JPanel();

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        changeWindowTitle();

        drawPrimary();
        drawTitle();
        drawContent();
        drawCategories();

        getWindow().add(panel);
    }

    /** change the title of the window */
    protected void changeWindowTitle() {
        getWindow().setTitle("Pet Rescue Saga - " + title);
    }

    /** show the content before the title */
    protected void drawPrimary() {

    }

    /** show the title */
    protected void drawTitle() {
        JPanel topPanel = new JPanel();
        topPanel.add(new Label(title, 40, 0, 0, 40, 0).asJLabel());
        panel.add(topPanel);
    }

    /** show the title */
    protected void drawContent() {

    }

    /** show the categories */
    protected void drawCategories() {
        JPanel contentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constr = new GridBagConstraints();
        constr.insets = new Insets(0, 0, 10, 0);
        constr.anchor = GridBagConstraints.CENTER;
        constr.gridy = -1;

        if (categories != null) {
            for (Pair<String, ActionListener> category : categories) {
                JButton button = new JButton(category.getObject1());

                if (category.getObject2() != null) {
                    button.addActionListener(category.getObject2());
                }

                constr.gridy = ++constr.gridy;
                contentPanel.add(button, constr);
            }
        }

        if (canGoBack) {
            JButton quitButton = new JButton("Retour");
            quitButton.addActionListener(e -> PetRescueSaga.view.goBack());
            constr.gridy = ++constr.gridy;
            contentPanel.add(quitButton, constr);
        }

        JButton quitButton = new JButton("Quitter");
        quitButton.addActionListener(e -> getWindow().quit());
        constr.gridy = ++constr.gridy;
        contentPanel.add(quitButton, constr);

        panel.add(contentPanel);
    }

    /** @return the window */
    public static Window getWindow() {
        return ((GuiView)PetRescueSaga.view).getWindow();
    }
}
