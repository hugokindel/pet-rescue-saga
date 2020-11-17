package com.g10.prs.view.ui;

public abstract class Menu extends Element {
    private final String title;
    private final String[] categories;
    private final boolean canGoBack;

    public Menu(String title, String[] categories) {
        this(title, categories, true);
    }

    public Menu(String title, String[] categories, boolean canGoBack) {
        super(ReturnType.Answer);

        this.title = title;
        this.categories = categories;
        this.canGoBack = canGoBack;
        this.answered = false;
    }

    public void handleAnswer() {

    }

    public String getTitle() {
        return title;
    }

    public boolean canGoBack() {
        return canGoBack;
    }

    public String[] getCategories() {
        return categories;
    }

    public int getAnswer() {
        return Integer.parseInt(askForAnswer());
    }
}