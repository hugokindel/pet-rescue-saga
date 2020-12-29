package com.g10.prs.view.gui;

import com.g10.prs.PetRescueSaga;
import com.g10.prs.common.Pair;
import com.g10.prs.common.Resources;
import com.g10.prs.view.Menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/** base class for Gui Menu */
public abstract class GuiMenu extends Menu {
    protected final Color BackgroundColor = new Color(144, 229, 249);
    protected final Color TextColor = new Color(14, 138, 164);

    private JPanel internalPanel;

    /** panel with the content */
    JPanel panel;
    /** the action that can be made in the menu */
    Pair<String, ActionListener>[] categories;
    /** background image */
    BufferedImage backgroundImage;
    /** background image */
    BufferedImage titleImage;

    /** class constructor */
    public GuiMenu(String title, Pair<String, ActionListener>[] categories, boolean canGoBack, String backgroundImagePath, String titleImagePath) {
        super(title, canGoBack);

        Resources.loadContent();

        this.categories = categories;
        this.backgroundImage = null;

        if (backgroundImagePath != null) {
            try {
                backgroundImage = Resources.getImage(backgroundImagePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (titleImagePath != null) {
            try {
                titleImage = Resources.getImage(titleImagePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public GuiMenu(String title, Pair<String, ActionListener>[] categories, boolean canGoBack, String backgroundImagePath) {
        this(title, categories, canGoBack, backgroundImagePath, null);
    }

    /** class constructor */
    public GuiMenu(String title) {
        this(title, null, true, "background.png", null);
    }

    /** show the Menu */
    @Override
    public void draw() {
        JPanel mainPanel = null;

        if (getView().getStyle() == GuiView.Style.Stylized && backgroundImage != null) {
            mainPanel = new Panel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(backgroundImage, 0, 0, getWindow().getWidth(), getWindow().getHeight(), null);
                }
            };
        }

        internalPanel = new Panel();
        internalPanel.setLayout(new BoxLayout(internalPanel, BoxLayout.Y_AXIS));

        panel = new Panel(true);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        changeWindowTitle();

        drawPrimary();
        drawTitle();
        drawContent();
        drawCategories();
        drawLast();

        internalPanel.add(panel);

        if (mainPanel != null) {
            mainPanel.add(internalPanel);
            getWindow().add(mainPanel);
        } else {
            getWindow().add(internalPanel);
        }

        getWindow().repaint();
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
        JPanel topPanel = new Panel();

        if (getView().getStyle() == GuiView.Style.Stylized && titleImage != null) {
            topPanel.add(new Label(new ImageIcon(titleImage), 20, 0, 40, 0));
        } else {
            topPanel.add(new Label(title, 40, 0, 0, 40, 0));
        }

        internalPanel.add(topPanel);
    }

    /** show the title */
    protected void drawContent() {

    }

    /** show the categories */
    protected void drawCategories() {
        JPanel contentPanel = new Panel(new GridBagLayout());

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
            JButton backButtpn = new JButton("Retour");
            backButtpn.addActionListener(e -> PetRescueSaga.view.goBack());
            constr.gridy = ++constr.gridy;
            contentPanel.add(backButtpn, constr);
        }

        JButton quitButton = new JButton("Quitter");
        quitButton.addActionListener(e -> getWindow().quit());
        constr.gridy = ++constr.gridy;

        contentPanel.add(quitButton, constr);

        panel.add(contentPanel);
    }

    public void drawLast() {
        if (getView().getStyle() == GuiView.Style.Stylized) {
            panel.setBackground(new Color(144, 229, 249));
            panel.setOpaque(false);

        }
    }

    /** @return the window */
    public static Window getWindow() {
        return ((GuiView)PetRescueSaga.view).getWindow();
    }

    public static GuiView getView() {
        return ((GuiView)PetRescueSaga.view);
    }
}
