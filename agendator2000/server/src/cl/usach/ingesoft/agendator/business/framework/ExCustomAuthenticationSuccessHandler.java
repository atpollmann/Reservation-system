package cl.usach.ingesoft.agendator.business.framework;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class ExCustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        handle(request, response, authentication);
    }

    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(authentication);
        if (response.isCommitted()) {
            return;
        }
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    /** Builds the target URL according to the logic defined in the main class Javadoc. */
    protected String determineTargetUrl(Authentication authentication) {
        boolean isPatient = false;
        boolean isProfessional = false;
        boolean isAdministrator = false;
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("ROLE_PATIENT")) {
                isPatient = true;
                break;
            } else if (grantedAuthority.getAuthority().equals("ROLE_PROFESSIONAL")) {
                isProfessional = true;
            } else if (grantedAuthority.getAuthority().equals("ROLE_ADMINISTRATOR")) {
                isAdministrator = true;
                break;
            }
        }

        if (isPatient || isProfessional) {
            return "/app/calendar/index.html";
        } else if (isAdministrator) {
            return "/app/user/index.html";
        } else {
            throw new IllegalStateException("unknown kind of user!");
        }
    }
}
