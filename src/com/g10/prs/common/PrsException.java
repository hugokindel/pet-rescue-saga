package com.g10.prs.common;

import com.g10.prs.common.print.Color;
import com.g10.prs.common.print.Out;
import com.g10.prs.common.print.TextColor;

public class PrsException extends Exception {
    public PrsException(String message){
        super(message);
    }

    @Override
    public String getMessage() {
        return TextColor.Red + Out.getPrefix() + Out.getErrorPrefix() + Color.ResetAll + " " + super.getMessage() + Color.ResetAll;
    }
}
