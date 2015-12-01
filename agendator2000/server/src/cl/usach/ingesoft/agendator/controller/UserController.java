package cl.usach.ingesoft.agendator.controller;

import cl.usach.ingesoft.agendator.business.dao.ISpecialityDao;
import cl.usach.ingesoft.agendator.business.service.IUsersService;
import cl.usach.ingesoft.agendator.business.validator.Validator;
import cl.usach.ingesoft.agendator.entity.*;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = Logger.getLogger(UserController.class);

    @Autowired private IUsersService usersService;

    @RequestMapping("/index.html")
    public ModelAndView index() {
        logger.info("handling default method");
        return new ModelAndView("frame")
                .addObject("availableUser", null/*userAdministration.findAllUsers()*/)
                .addObject("module", 7);
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