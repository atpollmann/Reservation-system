package cl.usach.ingesoft.agendator.controller;

import cl.usach.ingesoft.agendator.business.service.IUsersService;
import cl.usach.ingesoft.agendator.business.validator.Validator;
import cl.usach.ingesoft.agendator.controller.base.BaseController;
import cl.usach.ingesoft.agendator.entity.UserEntity;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/profile")
public class ProfileController extends BaseController {

    @Autowired private IUsersService usersService;

    @RequestMapping("/index.html")
    public ModelAndView index(
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        return newFrame(request)
                .selectMenu("profile")
                .selectContent("view/profile")

                .build();
    }


    @RequestMapping(value = "/updateProfile", method = RequestMethod.POST)
    public View updateUser(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("run") String run,

            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,

            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            UserEntity user = getUserFromSession(request.getSession());
            Validator.shouldBeFound(user);

            Validator.shouldNotBeEmpty(firstName);
            Validator.shouldNotBeEmpty(lastName);
            Validator.shouldNotBeEmpty(email);
            Validator.shouldBeEmail(email);
            int numericRun = Validator.shouldBeValidRun(run);

            user.setFirstName(firstName);
            user.setLastName(lastName);
            //user.setEmail(email);
            user.setRun(numericRun);

            if (currentPassword.isEmpty()) {
                Validator.shouldBeEmpty(newPassword);
                Validator.shouldBeEmpty(confirmPassword);
            } else {
                Validator.shouldNotBeEmpty(newPassword);
                Validator.shouldNotBeEmpty(confirmPassword);
                Validator.shouldBeEquals(newPassword, confirmPassword);
                user.setHashedPassword(DigestUtils.sha1Hex(newPassword));
            }

            usersService.updateUser(user);

            return defaultSuccessPage("index.html", "Perfil actualizado!");
        } catch (Exception e) {
            e.printStackTrace();
            return defaultErrorPage(e);
        }
    }
}