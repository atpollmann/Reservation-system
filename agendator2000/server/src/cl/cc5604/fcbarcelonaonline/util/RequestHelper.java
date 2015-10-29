package cl.cc5604.fcbarcelonaonline.util;

import cl.cc5604.fcbarcelonaonline.entity.UserEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: rene
 * Date: 23-06-13
 * Time: 04:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class RequestHelper {

    public static int getInt(HttpServletRequest request, String attributeName) {
        Object o = request.getAttribute(attributeName);
        return (o instanceof Integer ? (Integer) o : 0);
    }

}
