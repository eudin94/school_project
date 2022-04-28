package com.comerlato.school_project.util.helper;

import com.comerlato.school_project.exception.ErrorCodeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;

import javax.annotation.PostConstruct;
import java.util.Locale;

@RequiredArgsConstructor
public class MessageHelper {

    private final MessageSource messageSource;

    private MessageSourceAccessor accessor;

    @PostConstruct
    public void init() {
        accessor = new MessageSourceAccessor(messageSource, Locale.getDefault());
    }

    public String get(ErrorCodeEnum code, Object... args) {
        return accessor.getMessage(code.getMessageKey(), args);
    }
}
