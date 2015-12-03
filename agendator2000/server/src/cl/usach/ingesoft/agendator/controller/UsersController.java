package cl.usach.ingesoft.agendator.controller;

import cl.usach.ingesoft.agendator.business.service.IUsersService;
import cl.usach.ingesoft.agendator.controller.base.BaseController;
import cl.usach.ingesoft.agendator.entity.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UsersController extends BaseController {

    private static final Logger logger = Logger.getLogger(UsersController.class);

    @Autowired private IUsersService usersService;

    @RequestMapping("/index.html")
    public ModelAndView index() {
        logger.info("handling default method");

        return newFrame()
                .selectMenu("users")
                .selectContent("view/user")
                .add("users", usersService.findAllUsers())
                .build();
    }

    @RequestMapping(value = "/error.html", method = RequestMethod.GET)
    public ModelAndView error(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return new ModelAndView("frame")
                .addObject("module", -1);
    }

    @ResponseBody
    @RequestMapping(value = "/all_users", produces = "text/plain", method = RequestMethod.GET)
    public String availableUsername(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            List<UserEntity> allUsers = usersService.findAllUsers();

            StringBuilder sb = new StringBuilder();

            for(UserEntity ue : allUsers) {
                sb.append(ue.getLastName());
                sb.append(',');
                sb.append(ue.getFirstName());
                sb.append(':');
                sb.append(ue.getEmail());
                sb.append('\n');
            }

            return sb.toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return "error, "+e.getMessage();
        }
    }

}