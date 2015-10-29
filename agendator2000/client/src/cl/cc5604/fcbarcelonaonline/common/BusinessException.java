package cl.cc5604.fcbarcelonaonline.common;

/**
 * Created with IntelliJ IDEA.
 * User: rene
 * Date: 07-05-13
 * Time: 02:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Throwable throwable) {
        super(throwable);
    }

    public BusinessException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
