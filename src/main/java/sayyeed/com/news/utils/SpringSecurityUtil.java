package sayyeed.com.news.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import sayyeed.com.news.config.CustomUserDetails;

public class SpringSecurityUtil {

    public static Integer currentProfileId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        return user.getId();
    }

}