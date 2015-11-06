package cl.usach.ingesoft.agendator.util.controller;

import cl.usach.ingesoft.agendator.util.business.validator.Validator;
import cl.usach.ingesoft.agendator.entity.UserEntity;
import cl.usach.ingesoft.agendator.facade.IUserAdministration;
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
    private IUserAdministration userAdministration;

    @RequestMapping("/index.html")
    public ModelAndView index() {

        logger.info("handling default method");

        return new ModelAndView("frame")
                .addObject("availableUser", userAdministration.findAllUsers())
                .addObject("module", 7);
    }

    @RequestMapping(value = "/error.html", method = RequestMethod.GET)
    public ModelAndView error(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return new ModelAndView("frame").addObject("module", -1);
    }

    @RequestMapping(value = "/handlePost", method = RequestMethod.POST)
    public View handlePost(
            @RequestParam("id") String id,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("passwordConfirm") String passwordConfirm,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            // necesarias? el BO ya las hace ...
            Validator.shouldNotBeEmpty(username);
            Validator.shouldNotBeEmpty(password);
            Validator.shouldNotBeEmpty(passwordConfirm);
            Validator.shouldBeEquals(password, passwordConfirm);
            Validator.shouldBeUsername(username);
            // ..............................

            // create
            if (id == null || "".equals(id)) {

                UserEntity newUser = new UserEntity();
                newUser.setId(null);
                newUser.setUsername(username);
                newUser.setPassword(password);

                userAdministration.createUser(newUser);

                logger.info("new user created (id=" + newUser.getId() + ",username=" + username + ", password=?)");
            }
            // update
            else {
                Validator.shouldBeNumeric(id);

                int idUser = Integer.parseInt(id);

                UserEntity oldUser = new UserEntity();
                oldUser.setId(idUser);
                oldUser.setUsername(username);
                oldUser.setPassword(password);

                userAdministration.updateUser(oldUser);

                logger.info("user updated (id=" + oldUser.getId() + ", username=" + username + ", password=?)");
            }

            logger.info("redirecting from user/handlePost to user");

            return new RedirectView("index.html");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new RedirectView("error.html");
        }
    }

    @RequestMapping(value = "/handleDelete", method = RequestMethod.POST, produces = "text/plain")
    public
    @ResponseBody
    String handleDelete(
            @RequestParam("id") String id,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            // necesarias? el BO ya las hace ...
            Validator.shouldNotBeEmpty(id);
            Validator.shouldBeNumeric(id);
            // ..............................

            int idUser = Integer.parseInt(id);

            userAdministration.deleteUser(idUser);

            logger.info("deleted user id:" + id);

            return "ok";
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return "error, " + e.getMessage();
        }
    }

    @RequestMapping(value = "/availableUsername", produces = "text/plain", method = RequestMethod.POST)
    public
    @ResponseBody
    String availableUsername(
            @RequestParam("username") String username,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            Validator.shouldNotBeEmpty(username);

            username = username.trim().toLowerCase();

            logger.info("listing available usernames for '" + username + "'");

            return (userAdministration.findByUsername(username) == null ? "yes" : "no");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return "error, "+e.getMessage();
        }
    }

}