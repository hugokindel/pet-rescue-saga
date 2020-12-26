package com.g10.prs.view.cli;

import com.g10.prs.view.Menu;
import com.g10.prs.view.View;

/** Cli view class */
public class CliView extends View {
    public CliView() {
        super(new MainMenu());
    }
}
