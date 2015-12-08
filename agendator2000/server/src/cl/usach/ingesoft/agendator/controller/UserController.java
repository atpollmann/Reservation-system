package cl.usach.ingesoft.agendator.controller;

import cl.usach.ingesoft.agendator.business.service.IUsersService;
import cl.usach.ingesoft.agendator.controller.base.BaseController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
public class UserController extends BaseController {
    @Autowired private IUsersService usersService;

    @RequestMapping("/index.html")
    public ModelAndView index(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return newFrame(request)
                .selectMenu("user")
                .selectContent("view/user")
                .add("users", usersService.findAllUsers())
                .build();
    }

}