package cl.usach.ingesoft.agendator.util.business.application;

import cl.usach.ingesoft.agendator.util.business.application.captcha.CageCaptchaServlet;
import cl.usach.ingesoft.agendator.util.business.application.captcha.CaptchaSingleton;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ExUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        HttpSession session = request.getSession(true); // get session

        String errorMsg = null;

        // get previously stored response (set by CageCaptchaServlet)

        Object objCaptchaResponse = session.getAttribute(CageCaptchaServlet.CAPTCHA_RESPONSE);

        String captchaCode = request.getParameter(CageCaptchaServlet.CAPTCHA_CODE); // input from form submit
        String captchaResponse = (objCaptchaResponse == null ? null : String.valueOf(objCaptchaResponse));

        // perform validation of captcha
        if (captchaCode == null || captchaResponse == null) {
            errorMsg = "Invalid session.";
        } else if (!captchaCode.equals(captchaResponse)) {
            errorMsg = "Invalid captcha.";
        }

        // store status of validation for later exception
        ThreadLocal<String> threadLocal = CaptchaSingleton.getInstance().getThreadLocal();
        threadLocal.set(errorMsg);

        return super.attemptAuthentication(request, response);
    }
}