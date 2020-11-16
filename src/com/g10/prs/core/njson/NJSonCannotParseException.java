package com.g10.prs.core.njson;

import com.g10.prs.core.printer.Color;
import com.g10.prs.core.printer.Out;
import com.g10.prs.core.printer.TextColor;

public class NJSonCannotParseException extends Exception {
    public NJSonCannotParseException(String message){
        super(message);
    }

    @Override
    public String getMessage() {
        return TextColor.Red + Out.prefix + Out.error + Color.ResetAll + " " + super.getMessage() + Color.ResetAll;
    }
}
