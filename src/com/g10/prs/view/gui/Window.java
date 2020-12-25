package com.g10.prs.view.gui;

import com.g10.prs.common.print.Out;

import javax.swing.*;

public class Window extends JFrame {
    public Window() {
        setTitle("Pet Rescue Saga");
        setSize(960, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                Out.end();
            }
        });
    }

    public void quit() {
        setVisible(false);
        dispose();
    }
}
