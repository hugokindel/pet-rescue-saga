package com.g10.prs.view.gui;

import javax.swing.*;

/** Menu that show the credits */
public class CreditsMenu extends GuiMenu {

    /** class constructor */
    public CreditsMenu() {
        super("Crédits", null, true, "background.png", "credits.png");
    }

    /** show the content */
    @Override
    protected void drawContent() {
        Panel contentPanel = new Panel();
        contentPanel.add(new Label(("Programmeurs").toUpperCase(), getView().getStyle() == GuiView.Style.Stylized ? 26 : 18, 0, 0, 0, 25));
        contentPanel.add(new Label("<html>Hugo KINDEL<br>Maxime JAUROYON</html>", getView().getStyle() == GuiView.Style.Stylized ? 21 : 12));
        panel.add(contentPanel);

        Panel contentPanel2 = new Panel();
        contentPanel2.add(new Label(("Merci à").toUpperCase(), getView().getStyle() == GuiView.Style.Stylized ? 26 : 18, 0, 0, 0, 25));
        contentPanel2.add(new Label("<html>King<br>L'Université de Paris</html>", getView().getStyle() == GuiView.Style.Stylized ? 21 : 12));
        panel.add(contentPanel2);

        Panel contentPanel3 = new Panel();
        contentPanel3.add(new Label("Merci d'avoir joué à Pet Rescue Saga !", getView().getStyle() == GuiView.Style.Stylized ? 21 : 12, 25, 0, 0, 0));
        contentPanel3.setBorder(0, 0, 20, 0);
        panel.add(contentPanel3);
    }
}
