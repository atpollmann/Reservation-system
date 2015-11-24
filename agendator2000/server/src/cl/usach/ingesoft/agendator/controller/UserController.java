package cl.usach.ingesoft.agendator.controller;

import cl.usach.ingesoft.agendator.business.service.IUsersService;
import cl.usach.ingesoft.agendator.business.validator.Validator;
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

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private IUsersService usersService;

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
    @RequestMapping(value = "/handleDelete", method = RequestMethod.POST, produces = "text/plain")
    public String handleDelete(
            @RequestParam("id") Integer id,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            /*userAdministration.deleteUser(id);*/

            logger.info("deleted user id:" + id);

            return "ok";
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return "error, " + e.getMessage();
        }
    }

    @RequestMapping(value = "/handlePost", method = RequestMethod.POST)
    public View handlePost(
            @RequestParam("id") Integer id,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("passwordConfirm") String passwordConfirm,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            // necesarias? el BO ya las hace ...
            Validator.shouldNotBeEmpty(username);
            Validator.shouldBePassword(password);
            Validator.shouldNotBeEmpty(passwordConfirm);
            Validator.shouldBeEquals(password, passwordConfirm);
            Validator.shouldBeUsername(username);
            // ..............................

            // create
            if (id == null) {
                //userAdministration.createUser(username, password);
            }
            // update
            else {
                //userAdministration.updateUser(id, username, password);
            }

            logger.info("redirecting from user/handlePost to user");

            return new RedirectView("index.html");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new RedirectView("error.html");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/availableUsername", produces = "text/plain", method = RequestMethod.POST)
    public String availableUsername(
            @RequestParam("username") String username,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            Validator.shouldNotBeEmpty(username);

            username = username.trim().toLowerCase();

            logger.info("listing available usernames for '" + username + "'");

            return "yes";//(userAdministration.findByEmail(username) == null ? "yes" : "no");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return "error, "+e.getMessage();
        }
    }

}