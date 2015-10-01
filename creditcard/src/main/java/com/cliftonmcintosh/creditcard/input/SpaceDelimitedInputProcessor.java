package com.cliftonmcintosh.creditcard.input;

import com.google.common.base.Splitter;

import java.util.List;

/**
 * Created by cmcintosh on 9/20/15.
 */
public class SpaceDelimitedInputProcessor implements InputProcessor {

    @Override
    public List<String> processRawInput(String string) {
        return Splitter.on(" ").trimResults().omitEmptyStrings().splitToList(string);
    }
}
