package com.g10.prs.util;

import com.g10.prs.util.print.Color;
import com.g10.prs.util.print.Out;
import com.g10.prs.util.print.TextColor;

public class PrsException extends Exception {
    public PrsException(String message){
        super(message);
    }

    @Override
    public String getMessage() {
        return TextColor.Red + Out.prefix + Out.error + Color.ResetAll + " " + super.getMessage() + Color.ResetAll;
    }
}
