package com.shopp.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import java.util.IllegalFormatException;
import java.util.Locale;

@Component
public class Translator extends MessageSourceAccessor {

    @Autowired
    public Translator(MessageSource messageSource) {
        //super(messageSource, LocaleContextHolder.getLocale());
        super(messageSource, Locale.ENGLISH);
    }

    public String translate(String key, Object... args) throws IllegalFormatException, NoSuchMessageException {
        return this.getMessage(key, args);
    }

}
