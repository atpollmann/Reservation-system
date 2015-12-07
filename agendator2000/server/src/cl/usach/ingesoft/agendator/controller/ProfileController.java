package cl.usach.ingesoft.agendator.controller;

import cl.usach.ingesoft.agendator.controller.base.BaseController;
import cl.usach.ingesoft.agendator.entity.UserEntity;
import com.sun.deploy.net.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/profile")
public class ProfileController extends BaseController {

    @RequestMapping("/index.html")
    public ModelAndView index(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        HttpSession session = request.getSession();

        maybeCompleteSession(session);
        UserEntity currentUser = getSessionUser(session);

        return newFrame()
                .selectMenu("profile")
                .selectContent("view/profile")
                .add("currentUser", currentUser)
                .build();
    }

}