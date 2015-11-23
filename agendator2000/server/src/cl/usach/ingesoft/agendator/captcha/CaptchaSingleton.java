package cl.usach.ingesoft.agendator.captcha;

public class CaptchaSingleton {

    private static CaptchaSingleton instance;

    private CaptchaSingleton(){

    }

    public static CaptchaSingleton getInstance() {
        if (instance == null) {
            instance = new CaptchaSingleton();
        }
        return instance;
    }

    private final ThreadLocal<String> local = new ThreadLocal<String>() {
        @Override protected String initialValue() {
            return null;
        }
    };

    public ThreadLocal<String> getThreadLocal() {
        return local;
    }

}
