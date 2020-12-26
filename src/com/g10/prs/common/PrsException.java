package com.g10.prs.common;

import com.g10.prs.common.print.Color;
import com.g10.prs.common.print.Out;
import com.g10.prs.common.print.TextColor;
/** exception when parsing */
public class PrsException extends Exception {

    /** class constructor */
    public PrsException(String message){
        super(message);
    }

    /** returns the detailed message of this Throwable instance */
    @Override
    public String getMessage() {
        return TextColor.Red + Out.getPrefix() + Out.getErrorPrefix() + Color.ResetAll + " " + super.getMessage() + Color.ResetAll;
    }
}
