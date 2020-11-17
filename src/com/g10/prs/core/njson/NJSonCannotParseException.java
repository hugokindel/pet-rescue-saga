package com.g10.prs.core.njson;

import com.g10.prs.common.PrsException;

/** Exception for parsing related issues. */
public class NJSonCannotParseException extends PrsException {
    /**
     * Class constructor.
     *
     * @param message The message to pass.
     */
    public NJSonCannotParseException(String message){
        super(message);
    }
}
