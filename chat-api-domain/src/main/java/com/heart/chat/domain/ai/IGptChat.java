package com.heart.chat.domain.ai;

import java.io.UnsupportedEncodingException;

public interface IGptChat {

    public String answer(String question) throws Exception;
}
