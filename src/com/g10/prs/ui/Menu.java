package com.g10.prs.ui;

public class Menu implements Element<Integer> {
    private String title;
    private boolean canGoBack;
    private String[] categories;

    public Menu(String title, boolean canGoBack, String[] categories) {
        // TODO
    }

    public Integer use() {
        // TODO: print
        // TODO: ask next category from user (nextString, either a number or 'q' or 'b')
        // TODO: return result (important for back and quit)
        // TODO: return -1 for quit 'q'
        // TODO: return 0 for back 'b'
        return 1;
    }
}