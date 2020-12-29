package com.g10.prs.view.gui;

import com.g10.prs.PetRescueSaga;
import com.g10.prs.common.Resources;
import com.g10.prs.common.print.Out;

import javax.swing.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;

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
            public void windowStateChanged(WindowEvent e) {
                Out.print("tes");
                super.windowStateChanged(e);
            }

            @Override
            public void windowClosing(WindowEvent e) {
                ((GuiView)PetRescueSaga.view).setMusicState(false, false);
                Resources.saveSettings();
                Out.end();
            }

            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                ((GuiView)PetRescueSaga.view).setMusicState(false, false);
                Resources.saveSettings();
                Out.end();
            }
        });

        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {

            }

            @Override
            public void componentMoved(ComponentEvent e) {
                ((GuiView)PetRescueSaga.view).reload();
            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });
    }

    /** close the window (and the game) */
    public void quit() {
        setVisible(false);
        dispose();
    }
}
