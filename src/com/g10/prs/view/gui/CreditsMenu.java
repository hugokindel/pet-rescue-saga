package com.g10.prs.view.gui;

import javax.swing.*;

/** Menu that show the credits */
public class CreditsMenu extends GuiMenu {

    /** class constructor */
    public CreditsMenu() {
        super("Crédits");
    }

    /** show the content */
    @Override
    protected void drawContent() {
        JPanel contentPanel = new Panel();
        contentPanel.add(new Label(("Programmeurs").toUpperCase(), 18, 0, 0, 0, 25));
        contentPanel.add(new Label("<html>Hugo KINDEL<br>Maxime JAUROYON</html>"));
        panel.add(contentPanel);

        JPanel contentPanel2 = new Panel();
        contentPanel2.add(new Label("Merci d'avoir joué à Pet Rescue Saga !", 18, 25, 0, 0, 0));
        panel.add(contentPanel2);
    }
}
