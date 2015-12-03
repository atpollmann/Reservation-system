package cl.usach.ingesoft.agendator.controller;

import cl.usach.ingesoft.agendator.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/my_appointments")
public class MyAppointmentsController extends BaseController {

    @RequestMapping("/index.html")
    public ModelAndView index() {
        return newFrame()
                .selectMenu("my_appointments")
                .selectContent("view/main")
                .build();
    }

}