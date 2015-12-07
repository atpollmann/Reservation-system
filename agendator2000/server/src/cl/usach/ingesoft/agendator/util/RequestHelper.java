package cl.usach.ingesoft.agendator.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class RequestHelper {

    public static String getUserEmail() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user != null) {
            return user.getUsername();
        }
        return null;
    }
}
