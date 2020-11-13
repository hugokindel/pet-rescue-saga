package com.g10.prs.core.type;

import com.g10.prs.core.printer.Color;
import com.g10.prs.core.printer.Out;
import com.g10.prs.core.printer.TextColor;

public class PrsException extends Exception {
    public PrsException(String message){
        super(message);
    }

    @Override
    public String getMessage() {
        return TextColor.Red + Out.prefix + Out.error + Color.ResetAll + " " + super.getMessage() + Color.ResetAll;
    }
}