package com.g10.prs.view.graphic;

import com.g10.prs.view.View;
import com.g10.prs.view.ui.Menu;
import com.g10.prs.view.ui.Popup;

public class SwingView extends View {
    @Override
    public void showPopup(Popup popup,boolean v) {

    }

    @Override
    public int nextAnswer() {
        return 0;
    }

    @Override
    public String nextString() {
        return null;
    }

    @Override
    public int nextInt() {
        return 0;
    }

    @Override
    protected void drawMenu(Menu menu) {

    }
}
