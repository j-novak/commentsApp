package com.dummy.commentsApp.util;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static String getLogginInUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
