package com.cliftonmcintosh.creditcard.input;

import java.util.List;

/**
 * Created by cmcintosh on 9/20/15.
 */
public interface InputProcessor {

    List<String> processRawInput(String string);
}
