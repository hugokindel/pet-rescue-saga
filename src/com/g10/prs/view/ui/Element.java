package com.g10.prs.view.ui;

import com.g10.prs.PetRescueSaga;

public abstract class Element {
    protected boolean answered;
    protected String answer;
    protected ReturnType type;

    public Element(ReturnType type) {
        this.type = type;
    }

    public String askForAnswer() {
        if (!answered) {
            switch (type) {
                case Answer:
                    answer = String.valueOf(PetRescueSaga.view.nextAnswer());
                    break;
                case String:
                    answer = PetRescueSaga.view.nextString();
                    break;
                case Int:
                    answer = String.valueOf(PetRescueSaga.view.nextInt());
                    break;
            }

            answered = true;
        }

        return answer;
    }

    public void resetAnswer() {
        answer = "";
        answered = false;
    }

    public ReturnType getType() {
        return type;
    }
}
