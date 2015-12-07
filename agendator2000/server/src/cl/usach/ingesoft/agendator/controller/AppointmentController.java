package cl.usach.ingesoft.agendator.controller;

import cl.usach.ingesoft.agendator.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/appointment")
public class AppointmentController extends BaseController {

    @RequestMapping("/index.html")
    public ModelAndView index() {
        return newFrame()
                .selectMenu("appointment")
                .selectContent("view/appointment")
                .build();
    }

}