package cl.usach.ingesoft.agendator.controller.base;

import cl.usach.ingesoft.agendator.business.bo.ContextBO;
import cl.usach.ingesoft.agendator.business.service.IUsersService;
import cl.usach.ingesoft.agendator.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class BaseController {

    public class ControllerBuilder {

        private HttpServletRequest request;
        private ModelAndView modelAndView;
        private ContextBO contextBO;

        public ControllerBuilder selectMenu(String menuName) {
            modelAndView.addObject("menuName", menuName);
            return this;
        }

        public ControllerBuilder selectContent(String contentName) {
            modelAndView.addObject("contentName", contentName);
            return this;
        }

        private ControllerBuilder addContext() {
            maybeCompleteSession(request.getSession());
            addMessages();
            if (contextBO == null) {
                contextBO = new ContextBO();
                contextBO.setCurrentUser((UserEntity) request.getSession().getAttribute("current_user"));
            }
            modelAndView.addObject("context", contextBO);
            return this;
        }

        public ControllerBuilder add(String name, Object value) {
            modelAndView.addObject(name, value);
            return this;
        }

        public ModelAndView build() {
            return modelAndView;
        }

        private ControllerBuilder maybeCompleteSession(HttpSession session) {
            modelAndView.addObject("currentUser", getUserFromSession(session));
            return this;
        }

        private ControllerBuilder addMessages() {
            String errorStr = request.getParameter("error");
            String okStr = request.getParameter("ok");
            modelAndView.addObject("errorStr", errorStr != null ? pleaseEncode(errorStr) : null);
            modelAndView.addObject("okStr", okStr != null ? pleaseEncode(okStr) : null);
            return this;
        }
    }

    protected UserEntity getUserFromSession(HttpSession session) {
        if (session.getAttribute("current_user") == null) {
            String email = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
            session.setAttribute("current_user", usersService.findUserByEmail(email));
        }
        return (UserEntity) session.getAttribute("current_user");
    }

    @Autowired private IUsersService usersService;

    public ControllerBuilder newFrame(HttpServletRequest request) {
        return newFrame(request, "frame");
    }

    public ControllerBuilder newFrame(HttpServletRequest request, String jspName) {
        ControllerBuilder cb = new ControllerBuilder();
        cb.request = request;
        cb.modelAndView = new ModelAndView(jspName);
        return cb.addContext();
    }

    @RequestMapping("/error.html")
    public ModelAndView error(
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        return newFrame(request)
                .selectContent("error")
                .build();
    }

    protected RedirectView defaultSuccessPage(String viewName, String okStr) {
        if (viewName != null) {
            if (viewName.contains("?")) {
                viewName += ("&ok=" + pleaseEncode(okStr));
            } else {
                viewName += ("?ok=" + pleaseEncode(okStr));
            }
        }
        return new RedirectView(viewName);
    }

    protected RedirectView defaultErrorPage(Exception e) {
        return new RedirectView("error.html?error=" + pleaseEncode(e.getMessage()));
    }

    private static String pleaseEncode(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }
}
