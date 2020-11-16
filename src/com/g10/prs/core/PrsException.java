package com.g10.prs.core;

import com.g10.prs.core.printer.Color;
import com.g10.prs.core.printer.Out;
import com.g10.prs.core.printer.TextColor;

public class PrsException extends Exception {
    public PrsException(String message){
        super(message);
    }

    @Override
    public String getMessage() {
        return TextColor.Red + Out.getPrefix() + Out.getErrorPrefix() + Color.ResetAll + " " + super.getMessage() + Color.ResetAll;
    }
}
