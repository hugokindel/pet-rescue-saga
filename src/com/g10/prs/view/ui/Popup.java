package com.g10.prs.view.ui;

public abstract class Popup extends Element {
    private final String title;
    private final String description;

    public Popup(String title, String description, ReturnType type) {
        super(type);

        this.title = title;
        this.description = description;
    }

    public abstract void handleAnswer();

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    protected String getAnswer() {
        return askForAnswer();
    }
}