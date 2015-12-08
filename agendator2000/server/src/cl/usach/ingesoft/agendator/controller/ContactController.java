package cl.usach.ingesoft.agendator.controller;

import cl.usach.ingesoft.agendator.business.service.IEmailService;
import cl.usach.ingesoft.agendator.business.validator.Validator;
import cl.usach.ingesoft.agendator.controller.base.BaseController;
import cl.usach.ingesoft.agendator.entity.UserEntity;
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
@RequestMapping("/contact")
public class ContactController extends BaseController {

    @Autowired private IEmailService emailService;

    @RequestMapping("/index.html")
    public ModelAndView index(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return newFrame(request)
                .selectMenu("contact")
                .selectContent("view/contact")
                .build();
    }

    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    public View sendMessage(
            @RequestParam("subject") String subject,
            @RequestParam("message") String message,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            Validator.shouldNotBeEmpty(subject);
            Validator.shouldNotBeEmpty(message);

            UserEntity user = getUserFromSession(request.getSession());
            Validator.shouldBeFound(user);

            String subjectTemplate = "[agendator] El usuario (%s) ha enviado un mensaje a través de formulario de contacto.";
            String bodyTemplate = "Asunto:%s\nMensaje:%s";

            emailService.sendMessage(
                    "noreply.robot.rt@gmail.com",
                    "rtapiapincheira@gmail.com",
                    String.format(subjectTemplate, user.getEmail()),
                    String.format(bodyTemplate, subject, message)
            );
            return defaultSuccessPage("index.html", "Mensaje enviado!");
        } catch (Exception e) {
            e.printStackTrace();
            return defaultErrorPage(e);
        }
    }

}