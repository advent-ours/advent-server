package com.adventours.calendar.exception;

import com.adventours.calendar.exception.BaseException;
import com.adventours.calendar.exception.ResCode;

public class TokenNotFoundException extends BaseException {

    public TokenNotFoundException() {
        super(ResCode.CAL200);
    }

}
