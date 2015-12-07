package cl.usach.ingesoft.agendator.controller.base;

import cl.usach.ingesoft.agendator.business.service.IUsersService;
import cl.usach.ingesoft.agendator.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

public class BaseController {
    private ModelAndView modelAndView;

    @Autowired private IUsersService usersService;

    public BaseController newFrame() {
        return newFrame("frame");
    }

    public BaseController newFrame(String jspName) {
        modelAndView = new ModelAndView(jspName);
        return this;
    }

    public BaseController selectMenu(String menuName) {
        modelAndView.addObject("menuName", menuName);
        return this;
    }

    public BaseController selectContent(String contentName) {
        modelAndView.addObject("contentName", contentName);
        return this;
    }

    public BaseController add(String name, Object value) {
        modelAndView.addObject(name, value);
        return this;
    }

    public ModelAndView build() {
        return modelAndView;
    }

    public BaseController maybeCompleteSession(HttpSession session) {
        if (session.getAttribute("current_user") == null) {
            session.setAttribute("current_user", getCurrentUser());
        }
        return this;
    }

    protected UserEntity getSessionUser(HttpSession session) {
        return (UserEntity) session.getAttribute("current_user");
    }

    private UserEntity getCurrentUser() {
        String email = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        return usersService.findUserByEmail(email);
    }
}
