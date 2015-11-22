package cl.usach.ingesoft.agendator.view.util;

import javax.servlet.http.HttpServletRequest;

public class RequestHelper {
    public static int getInt(HttpServletRequest request, String attributeName) {
        Object o = request.getAttribute(attributeName);
        return (o instanceof Integer ? (Integer) o : 0);
    }
}
