package cl.usach.ingesoft.agendator.controller;

import cl.usach.ingesoft.agendator.controller.base.BaseController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/calendar")
@PreAuthorize("hasRole('ROLE_PATIENT') or hasRole('ROLE_PROFESSIONAL')")
public class CalendarController extends BaseController {

    @RequestMapping("/index.html")
    public ModelAndView index(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return newFrame(request)
                .selectMenu("calendar")
                .selectContent("view/calendar")
                .build();
    }

}