package com.g10.prs.view.gui;

import com.g10.prs.common.Resources;
import com.g10.prs.common.print.Out;

import javax.swing.*;

/** Window of the game */
public class Window extends JFrame {

    /** class constructor */
    public Window() {
        setTitle("Pet Rescue Saga");
        setSize(1200, 960);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        try {
            setIconImage(new ImageIcon(Resources.getImagesDirectory() + "/icon.png").getImage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                Out.end();
            }
        });
    }

    /** close the window (and the game) */
    public void quit() {
        setVisible(false);
        dispose();
    }
}
